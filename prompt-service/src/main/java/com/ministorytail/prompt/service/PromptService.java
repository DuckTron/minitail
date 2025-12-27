package com.ministorytail.prompt.service;

import org.springframework.stereotype.Service;

import com.ministorytail.prompt.model.FormattedPrompt;
import com.ministorytail.prompt.model.PromptBuilder;
import com.ministorytail.prompt.model.PromptRequest;

@Service
public class PromptService {

    private final PromptBuilder promptBuilder;

    public PromptService(PromptBuilder promptBuilder) {
        this.promptBuilder = promptBuilder;
    }

    public FormattedPrompt generatePrompt(PromptRequest request) {
        return promptBuilder.build(request);
    }
}