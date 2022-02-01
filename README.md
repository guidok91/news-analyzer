# Twitter API demo in Scala
![workflow](https://github.com/guidok91/twitter-api-demo/actions/workflows/ci.yml/badge.svg)

Scala 3 demo project that consumes and displays tweets from the Twitter API.

## Running instructions
The app needs a Bearer Token in order to authenticate against the API.

The token has to be be generated on the Twitter Developer portal. More info [here](https://developer.twitter.com/en/docs/twitter-api/getting-started/getting-access-to-the-twitter-api).

Keywords for tweet search must be specified in the [config file](conf/application.conf) (`tweet_keywords_query`).

Check the [Makefile](Makefile) for how to compile, test and run the application.

## CI/CD
A Github Actions workflow for CI/CD is defined [here](.github/workflows) and can be seen [here](https://github.com/guidok91/scala-example/actions).
