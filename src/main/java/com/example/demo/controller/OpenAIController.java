package com.example.demo.controller;

import com.example.demo.service.OpenAIService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/openai")
@CrossOrigin("*")
public class OpenAIController {

    @Autowired
    private OpenAIService openAIService;

    @PostMapping("/generate")
    public Map<String, String> generate(@RequestBody Map<String, String> request) {
        String prompt = request.get("prompt");
        String type = request.getOrDefault("type", "text");

        if (type.equalsIgnoreCase("image")) {
            String imageResponse = openAIService.generateImage(prompt);
            return Map.of(
                    "prompt", prompt,
                    "response_type", "image",
                    "response", "data:image/png;base64," + imageResponse
            );
        } else {
            String textResponse = openAIService.generateText(prompt);
            return Map.of(
                    "prompt", prompt,
                    "response_type", "text",
                    "response", textResponse
            );
        }
    }
}
