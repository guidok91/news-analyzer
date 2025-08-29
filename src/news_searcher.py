from collections import Counter
from config import TIME_PERIOD_MAP
from ddgs import DDGS
import streamlit as st


def search_news(topic: str, max_articles: int, time_period: str, region_language: str) -> list[dict[str, str]]:
    MAX_PAGES = 10

    with st.spinner(
        f'Searching for news about topic: "{topic}" in region/language: "{region_language}" '
        f"(limiting to max {max_articles} articles of the last {time_period.lower()})..."
    ):
        news_articles = []
        for page in range(1, MAX_PAGES + 1):
            news_articles_page_n = DDGS().news(
                query=f'"{topic}"',
                region=region_language,
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
    sources = {
        k: v
        for k, v in sorted(
            Counter(n["source"] for n in news_articles).items(),
            key=lambda item: item[1],
            reverse=True,
        )
    }
    st.write(
        f"Keeping the first {len(news_articles)} articles, from the following sources:\n"
        f"{'\n'.join([f'- {source}: {count} article(s).' for source, count in sources.items()])}"
    )

    return news_articles
