import argparse
import requests
from typing import List


TWEET_MAX_RESULTS = 50


def get_tweets(topic: str, twitter_api_token: str) -> List[str]:
    """
    Fetches tweets related to a specific topic using the Twitter API.
    
    Args:
        topic (str): The topic to search for.
        twitter_api_token (str): The Twitter API token for authentication.
    
    Returns:
        List[str]: A list of recent tweets about the topic.
    """
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
        raise Exception(f"Error fetching tweets: Error code: {response.status_code}. Error message: {response.text}")

    return [tweet["text"] for tweet in response.json()["data"]]


if __name__ == "__main__":
    parser = argparse.ArgumentParser(allow_abbrev=False)
    parser.add_argument("--topic", type=str, required=True)
    parser.add_argument("--twitter-api-token", type=str, required=True)
    args = parser.parse_args()

    print(f"Fetching tweets about: {args.topic}")
    print(f"Retrieved tweets: {get_tweets(args.topic, args.twitter_api_token)}")
