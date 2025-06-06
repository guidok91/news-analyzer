# Tweeter sentiment analysis

This is a Python app to analyse the overall sentiment about a specific topic in Twitter.

The app:
- Retrieves recent tweets from the [Twitter API](https://developer.twitter.com/en/docs/twitter-api) about a specified topic.
- Processes them with a LLM to get the overall sentiment about said topic.

## Running instructions

### Generate a token for the Twitter API
The token has to be be generated on the [Twitter Developer portal](https://docs.x.com/x-api/getting-started/getting-access).

The app needs a Bearer Token to authenticate against the API. More info [here](https://docs.x.com/resources/fundamentals/authentication/oauth-2-0/application-only).

### Run the app

```bash
make setup

make run TOPIC="<topic-to-analyse>" TWITTER_API_TOKEN="<your-twitter-api-token>"
```
