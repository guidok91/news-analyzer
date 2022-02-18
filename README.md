# Tweet sentiment analysis
![workflow](https://github.com/guidok91/twitter-api-demo/actions/workflows/ci.yml/badge.svg)

Scala app that retrieves tweets using the [Twitter API](https://developer.twitter.com/en/docs/twitter-api) and performs sentiment analysis with the [Stanford CoreNLP](https://stanfordnlp.github.io/CoreNLP/) library.

Tweets are retrieved based on search keywords we specify, and the tweet text is fed to the NLP library for sentiment analysis.

## Twitter API
The app needs a Bearer Token to authenticate against the API ([OAuth 2.0 App-Only](https://developer.twitter.com/en/docs/authentication/oauth-2-0/application-only) auth).

The token has to be be generated on the Twitter Developer portal. More info [here](https://developer.twitter.com/en/docs/twitter-api/getting-started/getting-access-to-the-twitter-api).

Once you have generated one, place it in the [config file](conf/application.conf) (`auth_bearer_token`).

Keywords for tweet search must also be specified in the [config file](conf/application.conf) (`tweet_search_keywords`).

Caveat: only tweets for the last week are retrieved (we use the `Recent search` option as opposed to the `Full-archive search`).

## Sentiment analysis
The `Stanford CoreNLP` library works by splitting a text into sentences and and assigning a sentiment value to each one:
* Values 0 or 1 => `negative` sentiment.
* Value 2 => `neutral` sentiment.
* Values 3 or 4 => `positive` sentiment.

Given this, if a tweet contains multiple sentences, we pick the most frequently assigned sentiment.

## Running instructions
Check the [Makefile](Makefile) for how to compile, test and run the application.

## CI/CD
A Github Actions workflow for CI/CD is defined [here](.github/workflows) and can be seen [here](https://github.com/guidok91/twitter-api-demo/actions).
