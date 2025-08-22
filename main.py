import ollama
import tiktoken
from ddgs import DDGS
import streamlit as st


TIME_PERIOD_MAP = {"Day": "d", "Week": "w", "Month": "m"}


def search_news(topic: str, max_articles: int, time_period: str) -> list[dict[str, str]]:
    MAX_PAGES = 10

    with st.spinner(
        f'Searching for news about topic: "{topic}" (limiting to max {max_articles} articles of the last {time_period.lower()})...'
    ):
        news_articles = []
        for page in range(1, MAX_PAGES + 1):
            news_articles_page_n = DDGS().news(
                query=f'"{topic}"',
                region="us-en",
                safesearch="off",
                timelimit=TIME_PERIOD_MAP[time_period],
                max_results=None,
                page=page,
                backend="auto",
            )
            news_articles = news_articles + news_articles_page_n

    st.write(f"Found {len(news_articles)} articles in total.")

    news_articles = list({article["url"]: article for article in news_articles}.values())
    st.write(f"Kept {len(news_articles)} articles (after deduplicating by URL).")

    news_articles = news_articles[:max_articles] if len(news_articles) > max_articles else news_articles
    sources = set(result["source"] for result in news_articles)
    st.write(
        f"Keeping the first {len(news_articles)} articles, from the following sources:\n"
        f"{'\n'.join([f'- {s}' for s in sources])}"
    )

    return news_articles


def analyze_news(topic: str, news_articles: list[dict[str, str]], llm: str) -> str:
    MAX_TOKEN_LENGTH = 16384

    with st.spinner(f'Summarizing news and analyzing overall sentiment of topic "{topic}" with LLM "{llm}"...'):
        news_text = "\n".join(
            (
                f"Article #{i+1} of {len(news_articles)}\n"
                f"- Date: {article['date']}\n"
                f"- Source: {article['source']}\n"
                f"- Title: {article['title']}\n"
                f"- Body: {article['body']}"
            )
            for i, article in enumerate(news_articles)
        )

        prompt = (
            f"""
            You are an expert news analyst.

            Based on the following news articles related to "{topic}", do the following:

            1. Summarize the key points and developments in no more than 100 words in total.
            2. Identify the overall sentiment (positive, negative, or neutral). One word only.
            3. Briefly justify your sentiment assessmentin 1 or 2 sentences and no more than 100 words in total.

            Provide your response in the following format:
            ## Summary\n
            <summary>
            ## Sentiment\n
            <sentiment>
            ## Justification\n
            <justification>

            If the articles are not in English, translate them to English first.
            Give your response in English.

            Articles:
            {news_text}
            """
        )

        prompt_token_length = len(tiktoken.get_encoding("cl100k_base").encode(prompt))
        if prompt_token_length > MAX_TOKEN_LENGTH:
            raise ValueError(
                f"Prompt exceeds maximum token length of {MAX_TOKEN_LENGTH} tokens. "
                f"Current length: {prompt_token_length} tokens. "
                "Consider reducing the maximum number of articles to retrieve or setting a higher MAX_TOKEN_LENGTH."
            )

        response = ollama.chat(
            model=llm,
            messages=[{"role": "user", "content": prompt}],
            options={"num_ctx": MAX_TOKEN_LENGTH},
        )

    st.write(response["message"]["content"])


if __name__ == "__main__":
    st.title("News Analyzer")
    st.sidebar.header("Articles Search Settings")

    topic = st.sidebar.text_input("Topic", value="Artificial Intelligence")
    llm = st.sidebar.text_input("LLM Model", value="llama3.1:8b")
    max_articles = st.sidebar.slider("Max Articles to Include", min_value=10, max_value=100, value=20, step=10)
    time_period = st.sidebar.selectbox(
        "Time Period",
        options=list(TIME_PERIOD_MAP.keys()),
        index=list(TIME_PERIOD_MAP.keys()).index("Week"),
    )

    if st.sidebar.button("Run Search"):
        news_articles = search_news(topic, max_articles, time_period)
        analyze_news(topic, news_articles, llm)
