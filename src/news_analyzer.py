import ollama
import streamlit as st
import tiktoken


def analyze_news(topic: str, news_articles: list[dict[str, str]], llm: str, region_language: str) -> str:
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

        prompt = f"""
            You are an expert news analyst that speaks multiple languages.

            Based on the following news articles related to "{topic}", of the region/language {region_language}, do the following:

            1. Summarize the key points and developments in no more than 100 words in total.
            2. Identify the overall sentiment (positive, negative, or neutral). One word only.
            3. Briefly justify your sentiment assessmentin 1 or 2 sentences and no more than 100 words in total.

            Provide your response in the following format, respecting the instructions above for each section:
            ## Summary\n
            <summary>
            ## Sentiment\n
            <sentiment>
            ## Justification\n
            <justification>

            Take into account that the articles can be in different languages, and you should be able to understand them all.
            Give your response in English.

            Do exactly as instructed and do not ask any follow up questions or add any further information.

            Articles:
            {news_text}
            """

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
