export TZ=UTC
export UV_VERSION=0.11.6

.PHONY: help
help:
	@awk -F ':.*# ' '/^[a-zA-Z_-]+:.*# / {printf "\033[32m%-20s\033[0m %s\n", $$1, $$2}' $(MAKEFILE_LIST)

.PHONY: setup
setup: # Set up virtual env with the app and its dependencies.
	curl -LsSf https://astral.sh/uv/$(UV_VERSION)/install.sh | sh
	uv sync

.PHONY: run
run: # Run the app.
	uv run streamlit run src/main.py
