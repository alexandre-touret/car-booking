package info.touret.ai.carbooking.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.ChatClient;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.SystemPromptTemplate;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class RAGService {
    private static final Logger LOGGER = LoggerFactory.getLogger(RAGService.class);

    @Value("classpath:prompt/cars-terms-of-use.st")
    private Resource systemPrompt;
    private final ChatClient chatClient;
    private final VectorStore vectorStore;

    public RAGService(ChatClient chatClient, VectorStore vectorStore) {
        this.chatClient = chatClient;
        this.vectorStore = vectorStore;
    }

    public AssistantMessage retrieve(String message) {

        LOGGER.info("Retrieving relevant documents");
        List<Document> similarDocuments = vectorStore.similaritySearch(message);
        LOGGER.info(String.format("Found %s relevant documents.", similarDocuments.size()));
        var prompt = new Prompt(List.of(getSystemMessage(similarDocuments), new UserMessage(message)));
        var chatResponse = chatClient.call(prompt);
        return chatResponse.getResult().getOutput();
    }

    private Message getSystemMessage(List<Document> similarDocuments) {
        String documents = similarDocuments.stream().map(Document::getContent).collect(Collectors.joining("\n"));
        SystemPromptTemplate systemPromptTemplate = new SystemPromptTemplate(systemPrompt);
        return systemPromptTemplate.createMessage(Map.of("documents", documents));
    }
}
