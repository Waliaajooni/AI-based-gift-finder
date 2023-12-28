package com.practice.giftfinder.service;

import com.practice.giftfinder.model.gpt.GptRequest;
import com.practice.giftfinder.model.gpt.GptResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestTemplate;

@Service
public class Gpt3Service {

    @Value("${openai.model}")
    private String model;

    @Value(("${openai.api.url}"))
    private String apiURL;

    @Autowired
    private RestTemplate template;

    public GptResponse getGPTResponseForPrompt(String prompt) {
        GptRequest request=new GptRequest(model, prompt);
        GptResponse chatGptResponse = template.postForObject(apiURL, request, GptResponse.class);
        return chatGptResponse;
    }
}
