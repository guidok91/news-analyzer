# News analyzer
Python [Streamlit](https://streamlit.io/) app to analyze news about a given topic using LLMs.

The app:
- Performs a web search for recent news articles of a given topic using [D.D.G.S.](https://github.com/deedy5/ddgs) library.
- Summarizes them and provides the overall sentiment using an LLM.

## Running instructions

### Setup Ollama locally
First, install and run [Ollama](https://ollama.com/) in your machine and then execute the following command to pull the LLM we will use for the app:
```bash
ollama pull llama3.2
```

Note: feel free to use other model of your choice.

### Run the app
Create the local env for the app:
```bash
make setup
```

Run the app:
```bash
make run
```
