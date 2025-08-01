import argparse
import logging

import ollama
from ddgs import DDGS


def search_news(topic: str) -> list[dict[str, str]]:
    logging.info(f'Searching for news about topic: "{topic}"...')

    news_articles = DDGS().news(
        query=f'{topic}',
        region="us-en",
        safesearch="off",
        timelimit="w",
        max_results=100,
        page=1,
        backend="auto",
    )

    sources = set(result["source"] for result in news_articles)
    logging.info(
        f"Found {len(news_articles)} articles from the following sources:\n" + "\n".join(f"- {s}" for s in sources)
    )

    return news_articles


def analyze_news(topic: str, news_articles: list[dict[str, str]], llm: str) -> str:
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

    response = ollama.chat(
        model=llm,
        messages=[{"role": "user", "content": prompt}],
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
    args = parser.parse_args()

    news_articles = search_news(args.topic)
    news_analysis = analyze_news(args.topic, news_articles, args.llm)

    logging.info(
        f'\n{"="*150}\n{news_analysis}\n{"="*150}\n'
    )
