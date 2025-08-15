import argparse
import logging

import ollama
import tiktoken
from ddgs import DDGS


def search_news(topic: str, max_articles: int) -> list[dict[str, str]]:
    MAX_PAGES = 10

    logging.info(f'Searching for news about topic: "{topic}" (limiting to max {max_articles} articles)...')
    news_articles = []
    for page in range(1, MAX_PAGES + 1):
        news_articles_page_n = DDGS().news(
            query=f'{topic}',
            region="us-en",
            safesearch="off",
            timelimit="w",
            max_results=None,
            page=page,
            backend="auto",
        )
        news_articles = news_articles + news_articles_page_n
    logging.info(f"Found {len(news_articles)} articles in total.")

    logging.info("Deduplicating articles based on URL...")
    news_articles = list({article["url"]: article for article in news_articles}.values())
    logging.info(f"Kept {len(news_articles)} articles.")

    news_articles = news_articles[:max_articles] if len(news_articles) > max_articles else news_articles
    sources = set(result["source"] for result in news_articles)
    logging.info(
        f"Keeping the first {len(news_articles)} articles, from the following sources:\n" + "\n".join(f"- {s}" for s in sources)
    )

    return news_articles


def analyze_news(topic: str, news_articles: list[dict[str, str]], llm: str) -> str:
    MAX_TOKEN_LENGTH = 16384

    logging.info(f'Summarizing news and analyzing overall sentiment of topic "{topic}" with LLM "{llm}"...')

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
        2. Identify the overall sentiment (positive, negative, or neutral).
        3. Briefly justify your sentiment assessment (1 or 2 sentences).

        Provide your response in the following format:
        ******************** Summary ********************
        <summary>
        ******************** Sentiment ********************
        <sentiment>
        ******************** Justification ********************
        <justification>

        If the articles are not in English, translate them to English first.
        Give your response in English.

        Articles:
        {news_text}
        """
    )
    logging.debug(f"Prompt for LLM:\n{prompt}")

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

    return response["message"]["content"]


def config_logging() -> None:
    logging.basicConfig(
        level=logging.INFO,
        format="%(asctime)s [%(levelname)s] %(message)s",
        datefmt="%Y-%m-%d %H:%M:%S",
    )
    logging.getLogger().handlers[0].addFilter(
        lambda record: record.name == "root" or record.levelno >= logging.WARNING
    )


if __name__ == "__main__":
    config_logging()

    parser = argparse.ArgumentParser(allow_abbrev=False)
    parser.add_argument("--topic", type=str, required=True)
    parser.add_argument("--llm", type=str, required=True)
    parser.add_argument("--max-articles", type=int, required=True)
    args = parser.parse_args()

    news_articles = search_news(args.topic, args.max_articles)
    news_analysis = analyze_news(args.topic, news_articles, args.llm)

    logging.info(
        f'\n{"="*150}\n{news_analysis}\n{"="*150}\n'
    )
