package com.ministorytail.prompt.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ministorytail.prompt.dto.PromptRequestDTO;
import com.ministorytail.prompt.dto.PromptResponseDTO;
import com.ministorytail.prompt.model.FormattedPrompt;
import com.ministorytail.prompt.model.PromptRequest;
import com.ministorytail.prompt.service.PromptService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/prompts")
public class PromptController {

    private final PromptService promptService;

    public PromptController(PromptService promptService) {
        this.promptService = promptService;
    }

    @PostMapping("/build")
    public ResponseEntity<PromptResponseDTO> buildPrompt(
            @Valid @RequestBody PromptRequestDTO requestDTO) {

        PromptRequest request = PromptRequest.create(
                requestDTO.getChildName(),
                requestDTO.getChildAge(),
                requestDTO.getSelectedInterest(),
                requestDTO.getTheme(),
                requestDTO.getStyle(),
                requestDTO.getNumberOfPages());

        FormattedPrompt prompt = promptService.generatePrompt(request);

        PromptResponseDTO response = new PromptResponseDTO(prompt.getPromptText());

        return ResponseEntity.ok(response);
    }
}