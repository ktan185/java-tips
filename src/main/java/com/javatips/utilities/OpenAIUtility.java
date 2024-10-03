package com.javatips.utilities;

public class OpenAIUtility {

    public static String getPrompt() {
        return """
            You are a Senior Java Developer at Google with extensive expertise in Java.
            Your task is to create an impactful lesson on an advanced and niche Java topic, 
            specifically for intermediate developers with solid Java experience.
                              
            Output a lesson in markdown format, ensuring clarity, depth, and relevance""";
    }
}
