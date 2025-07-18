import argparse
import logging

import ollama
from ddgs import DDGS


def search_news(topic: str) -> list[dict[str, str]]:
    logging.info(f'Searching for news about topic: "{topic}"...')

    news_articles = DDGS().news(
        query=topic,
        region="us-en",
        safesearch="off",
        timelimit="w",
        num_results=30,
        page=1,
        backend="duckduckgo",
    )

    sources = set(result["source"] for result in news_articles)
    logging.info(
        f"Found {len(news_articles)} articles from the following sources:\n" + "\n".join(f"- {s}" for s in sources)
    )

    return news_articles


def analyze_news(topic: str, news_articles: list[dict[str, str]], llm: str) -> str:
    logging.info(f'Summarizing news and analyzing overall sentiment of topic "{topic}" with LLM "{llm}"...')

    news_text = "\n".join(
        f"Date: {a['date']} - Source: {a['source']} - Title: {a['title']} - Body: {a['body']}" for a in news_articles
    )
    prompt = (
        f"""
        Given the following list of recent news articles about the topic "{topic}", 
        summarize the most relevant points and determine the overall sentiment analysis (positive, negative, neutral) 
        briefly explaining your reasoning, all in maximum 100 words:
        {news_text}
        """
    )

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
        f'\n{"="*80}\nNews summary and sentiment analysis for topic "{args.topic}":\n{news_analysis}\n{"="*80}\n'
    )
