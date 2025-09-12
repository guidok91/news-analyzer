import ollama

AVAILABLE_MODELS = [model.model for model in ollama.list().models]
TIME_PERIOD_MAP = {"Day": "d", "Week": "w", "Month": "m"}
REGIONS_LANGUAGES = ["us-en", "ar-es", "es-es", "de-de"]
