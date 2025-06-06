# Tweet sentiment analysis

This is a Python app to analyse the overall sentiment about a specific topic in Twitter.

The app:
- Retrieves recent tweets from the [Twitter API](https://developer.twitter.com/en/docs/twitter-api) about a specified topic.
- Processes them with a LLM to get the overall sentiment about said topic.

## Running instructions

### Generate a token for the Twitter API
The app needs a Bearer Token to authenticate against the API ([OAuth 2.0 App-Only](https://developer.twitter.com/en/docs/authentication/oauth-2-0/application-only) auth).

The token has to be be generated on the Twitter Developer portal. More info [here](https://developer.twitter.com/en/docs/twitter-api/getting-started/getting-access-to-the-twitter-api).

### Run the app

```bash
make setup

make run TOPIC=<topic-to-analyse> TWITTER_API_TOKEN=<your-twitter-api-token>
```
