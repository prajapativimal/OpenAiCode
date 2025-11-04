package com.example.demo.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.*;

@Service
public class OpenAIService {

    @Value("${openai.api.key}")
    private String apiKey;

    @Value("${openai.text.model}")
    private String textModel;

    @Value("${openai.image.model}")
    private String imageModel;

    private final RestTemplate restTemplate = new RestTemplate();

    // ---------- TEXT GENERATION ----------
    public String generateText(String prompt) {
        try {
            String url = "https://api.openai.com/v1/chat/completions";

            Map<String, Object> requestBody = new HashMap<>();
            requestBody.put("model", textModel);
            requestBody.put("messages", List.of(Map.of("role", "user", "content", prompt)));

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("Authorization", "Bearer " + apiKey);

            HttpEntity<Map<String, Object>> entity = new HttpEntity<>(requestBody, headers);

            ResponseEntity<Map> response = restTemplate.exchange(url, HttpMethod.POST, entity, Map.class);

            List<Map<String, Object>> choices = (List<Map<String, Object>>) response.getBody().get("choices");
            if (choices != null && !choices.isEmpty()) {
                Map<String, Object> message = (Map<String, Object>) choices.get(0).get("message");
                return (String) message.get("content");
            }
            return "No response text found.";
        } catch (Exception e) {
            return "Error generating text: " + e.getMessage();
        }
    }
    // ---------- IMAGE GENERATION ----------
    public String generateImage(String prompt) {
        try {
            String url = "https://api.openai.com/v1/images/generations";

            Map<String, Object> requestBody = new HashMap<>();
            requestBody.put("model", imageModel);
            requestBody.put("prompt", prompt);
            requestBody.put("size", "1024x1024");

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("Authorization", "Bearer " + apiKey);

            HttpEntity<Map<String, Object>> entity = new HttpEntity<>(requestBody, headers);
            ResponseEntity<Map> response = restTemplate.exchange(url, HttpMethod.POST, entity, Map.class);

            List<Map<String, Object>> data = (List<Map<String, Object>>) response.getBody().get("data");
            if (data != null && !data.isEmpty()) {
                return (String) data.get(0).get("b64_json");
            }

            return "No image generated.";
        } catch (Exception e) {
            return "Error generating image: " + e.getMessage();
        }
    }
}
