package com.javatips.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.javatips.utilities.OpenAIUtility;

import io.github.stefanbratanov.jvm.openai.ChatClient;
import io.github.stefanbratanov.jvm.openai.ChatCompletion;
import io.github.stefanbratanov.jvm.openai.ChatMessage;
import io.github.stefanbratanov.jvm.openai.CreateChatCompletionRequest;
import io.github.stefanbratanov.jvm.openai.OpenAI;
import io.github.stefanbratanov.jvm.openai.OpenAIModel;

@Service
public class OpenAIService {

    @Value("${openai.api.key}")
    String OPENAI_API_KEY;

    public String requestForJavaTip() {
        OpenAI openAI = OpenAI.newBuilder(OPENAI_API_KEY).build();
        ChatClient chatClient = openAI.chatClient();
        CreateChatCompletionRequest request = generateRequest(OpenAIUtility.getInitialPrompt());
        ChatCompletion chatCompletion = chatClient.createChatCompletion(request);
        String response = chatCompletion.choices().get(0).message().content();
        System.out.println(response);
        return response;
    }

    private CreateChatCompletionRequest generateRequest(String request) {
        return CreateChatCompletionRequest.newBuilder()
                .model(OpenAIModel.GPT_4o_MINI)
                .temperature(0.2)
                .frequencyPenalty(0.5)
                .topP(0.1)
                .message(ChatMessage.systemMessage(request))
                .build();
    }
}
