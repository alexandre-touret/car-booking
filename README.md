# How to run it?

## Azure OPENAI infos

Create the following environment variables first:

```jshelllanguage
export SPRING_AI_AZURE_OPENAI_API_KEY=...
export SPRING_AI_AZURE_OPENAI_ENDPOINT=https://mywebsite
```

## Database

```jshelllanguage
cd infrastructure
docker compose up -d
```

# How to test it?

```jshelllanguage
http :8080/ai/rag message=="what are the car booking requirements?"
```

```jshelllanguage
http :8080/ai/simple message==openai
```
