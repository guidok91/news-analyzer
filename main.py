import argparse


if __name__ == "__main__":
    parser = argparse.ArgumentParser(allow_abbrev=False)
    parser.add_argument("--topic", type=str, required=True)
    parser.add_argument("--twitter-api-token", type=str, required=True)
    args = parser.parse_args()

    print(f"Topic: {args.topic}")
    print(f"Twitter API Token: {args.twitter_api_token}")
