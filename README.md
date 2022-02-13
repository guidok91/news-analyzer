# Tweet sentiment analysis
![workflow](https://github.com/guidok91/twitter-api-demo/actions/workflows/ci.yml/badge.svg)

Scala app that retrieves tweets using the [Twitter API](https://developer.twitter.com/en/docs/twitter-api) and performs sentiment analysis with the [Stanford CoreNLP](https://stanfordnlp.github.io/CoreNLP/) library.

Only tweets for the last week are retrieved (we use the `Recent search` option as opposed to the `Full-archive search`).

## Running instructions
### Authentication against the Twitter API
The app needs a Bearer Token to authenticate against the API ([OAuth 2.0 App-Only](https://developer.twitter.com/en/docs/authentication/oauth-2-0/application-only) auth).

The token has to be be generated on the Twitter Developer portal. More info [here](https://developer.twitter.com/en/docs/twitter-api/getting-started/getting-access-to-the-twitter-api).

Once you have generated one, place it in the [config file](conf/application.conf) (`auth_bearer_token`).

### App config
Keywords for tweet search must be specified in the [config file](conf/application.conf) (`tweet_search_keywords`).

### Running the app
Check the [Makefile](Makefile) for how to compile, test and run the application.

## CI/CD
A Github Actions workflow for CI/CD is defined [here](.github/workflows) and can be seen [here](https://github.com/guidok91/twitter-api-demo/actions).
