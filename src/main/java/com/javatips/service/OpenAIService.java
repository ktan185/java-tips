package com.javatips.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

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

    public void sendPrompt() {
        System.out.println("This is the Key: " + OPENAI_API_KEY);
        OpenAI openAI = OpenAI.newBuilder(OPENAI_API_KEY).build();

        ChatClient chatClient = openAI.chatClient();
        CreateChatCompletionRequest createChatCompletionRequest = CreateChatCompletionRequest.newBuilder()
                .model(OpenAIModel.GPT_4o_MINI)
                .message(ChatMessage.userMessage("Who won the world series in 2020?"))
                .build();
        ChatCompletion chatCompletion = chatClient.createChatCompletion(createChatCompletionRequest);
        System.out.println(chatCompletion.choices().get(0).message().content());
    }
}
