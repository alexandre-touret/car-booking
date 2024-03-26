package info.touret.ai.carbooking.controller;

import info.touret.ai.carbooking.service.RAGService;
import org.springframework.ai.chat.ChatClient;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
/**
 * https://github.com/Azure-Samples/spring-ai-azure-workshop/blob/main/src/main/java/com/xkcd/ai/helloworld/SimpleAiController.java
 */

@RestController
public class SimpleAIController {
    private final ChatClient chatClient;
    private final RAGService ragService;


    public SimpleAIController(ChatClient chatClient, RAGService ragService) {
        this.chatClient = chatClient;
        this.ragService = ragService;
    }

    @GetMapping("/ai/simple")
    public Map<String, String> generation(
            @RequestParam(value = "message", defaultValue = "Tell me a joke") String message) {
        return Map.of("generation", chatClient.call(message));
    }

    @GetMapping("/ai/rag")
    public AssistantMessage  generate(
            @RequestParam(value = "message", defaultValue = "Can you introduce the Miles Service?") String message) {
        return ragService.retrieve(message);
    }

}
