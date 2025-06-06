import argparse
import logging
import ollama
import requests
from typing import List


TWEET_MAX_RESULTS = 50
LLM = "mistral"


def get_tweets(topic: str, twitter_api_token: str) -> List[str]:
    logging.info(f"Fetching tweets about topic: {topic}...")

    response = requests.request(
        method="GET",
        url="https://api.twitter.com/2/tweets/search/recent",
        headers={"Authorization": f"Bearer {twitter_api_token}"},
        params={
            "query": topic,
            "max_results": TWEET_MAX_RESULTS,
            "tweet.fields": "created_at,text,author_id",
        }
    )

    if response.status_code != 200:
        raise requests.exceptions.HTTPError(f"Error fetching tweets: Error code: {response.status_code}. Error message: {response.text}")

    tweets = [tweet["text"] for tweet in response.json()["data"]]

    logging.info(f"Retrieved {len(tweets)} tweets about {topic}")

    return tweets


def get_sentiment(topic: str, tweets: List[str]) -> str:
    logging.info(f"Analyzing sentiment of tweets with LLM {LLM}...")
    
    tweet_text = "\n".join([f"- {tweet}" for tweet in tweets])
    prompt = f"""
        Given the following list of recent tweets about the topic '{topic}',
        summarize the general sentiment (positive, negative, or mixed) and briefly explain why:
        {tweet_text}
    """

    response = ollama.chat(
        model=LLM,
        messages=[{"role": "user", "content": prompt}],
    )
    
    return response["message"]["content"]


if __name__ == "__main__":
    logging.basicConfig(level=logging.INFO)

    parser = argparse.ArgumentParser(allow_abbrev=False)
    parser.add_argument("--topic", type=str, required=True)
    parser.add_argument("--twitter-api-token", type=str, required=True)
    args = parser.parse_args()

    tweets = get_tweets(args.topic, args.twitter_api_token)

    sentiment = get_sentiment(args.topic, tweets)

    logging.info(f"Sentiment analysis result for topic {args.topic}:\n{sentiment}")
