from config import REGIONS_LANGUAGES, TIME_PERIOD_MAP
from news_analyzer import analyze_news
from news_searcher import search_news
import streamlit as st


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
    region_language = st.sidebar.selectbox(
        "Region/Language",
        options=REGIONS_LANGUAGES,
        index=REGIONS_LANGUAGES.index("us-en"),
    )

    if st.sidebar.button("Run Search"):
        news_articles = search_news(topic, max_articles, time_period, region_language)
        analyze_news(topic, news_articles, llm, region_language)
