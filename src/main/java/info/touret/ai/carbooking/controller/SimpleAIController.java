package info.touret.ai.carbooking.controller;

import org.springframework.ai.chat.ChatClient;
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


    public SimpleAIController(ChatClient chatClient) {
        this.chatClient = chatClient;
    }

    @GetMapping("/ai/simple")
    public Map<String, String> generation(
            @RequestParam(value = "message", defaultValue = "Tell me a joke") String message) {
        return Map.of("generation", chatClient.call(message));
    }
}
