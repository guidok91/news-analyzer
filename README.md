# Tweeter sentiment analysis
Python app to analyse the overall sentiment about a specific topic in Twitter using LLMs.

The app:
- Retrieves recent tweets from the [Twitter API](https://developer.x.com/en/docs/x-api) about a specified topic.
- Processes them with an LLM to get the overall sentiment about said topic.

## Running instructions

### Generate a token for the Twitter API
The token has to be be generated on the [Twitter Developer portal](https://docs.x.com/x-api/getting-started/getting-access).

The app needs a Bearer Token to authenticate against the API. More info [here](https://docs.x.com/resources/fundamentals/authentication/oauth-2-0/application-only).

### Install Ollama and download the required LLM
First, install [Ollama](https://ollama.com/) in your machine and then run the following command to pull the LLM we will use for the app:
```bash
ollama pull mistral
```

Note: feel free to use other model of your choice.

### Run the app
Create the local env for the app:
```bash
make setup
```

Run the app, for example:
```bash
make run TOPIC="Donald Trump" TWITTER_API_TOKEN="<your-twitter-api-token>"
```
