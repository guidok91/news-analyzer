# News analyzer
Python app that analyzes recent news about a given topic using LLMs.

The app:
- Performs a web search for recent news about a given topic using [D.D.G.S.](https://github.com/deedy5/ddgs) library.
- Summarizes it and provides the overall sentiment using an LLM.

## Running instructions

### Setup Ollama locally
First, install and run [Ollama](https://ollama.com/) in your machine and then execute the following command to pull the LLM we will use for the app:
```bash
ollama pull llama3.2
```

Note: you might need to set `Context length` to higher values (e.g. 8k, 16k) if you want to analyse larger amounts of news articles.  
Note: feel free to use other model of your choice.

### Run the app
Create the local env for the app:
```bash
make setup
```

Run the app, for example:
```bash
make run TOPIC="Donald Trump" LLM="llama3.2"
```

Please provide the topic in English language.
