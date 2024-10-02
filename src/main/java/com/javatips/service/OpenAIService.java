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

    public void requestForJavaTip() {
        OpenAI openAI = OpenAI.newBuilder(OPENAI_API_KEY).build();
        ChatClient chatClient = openAI.chatClient();
        CreateChatCompletionRequest createChatCompletionRequest = CreateChatCompletionRequest.newBuilder()
                .model(OpenAIModel.GPT_4o_MINI)
                .message(ChatMessage.systemMessage(OpenAIUtility.getInitialPrompt()))
                .build();
        ChatCompletion chatCompletion = chatClient.createChatCompletion(createChatCompletionRequest);
        String response = chatCompletion.choices().get(0).message().content();
        System.out.println(response);
    }
}
