package com.ministorytail.prompt.model;

import com.ministorytail.prompt.model.exceptions.InvalidPromptRequestException;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Value Object que representa um prompt formatado e pronto para ser enviado ao
 * Gemini.
 * 
 * Este é o resultado final do processo de construção de prompt.
 * Contém apenas o texto completo, já formatado e validado.
 * 
 * Invariante: promptText não pode ser null nem vazio.
 */
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class FormattedPrompt {

    private final String promptText;

    /**
     * Factory method para criar um FormattedPrompt válido.
     * 
     * @param promptText Texto do prompt formatado
     * @return FormattedPrompt válido
     * @throws InvalidPromptRequestException se o texto for null ou vazio
     */
    public static FormattedPrompt create(String promptText) {
        validatePromptText(promptText);
        return new FormattedPrompt(promptText);
    }

    /**
     * Valida o texto do prompt.
     * Regra: não pode ser null nem vazio.
     */
    private static void validatePromptText(String promptText) {
        if (promptText == null || promptText.trim().isEmpty()) {
            throw new InvalidPromptRequestException(
                    "Texto do prompt não pode ser vazio");
        }
    }

    /**
     * Retorna o tamanho do prompt em caracteres.
     * Útil para logging e debugging.
     * 
     * @return Número de caracteres do prompt
     */
    public int length() {
        return promptText.length();
    }

    /**
     * Retorna uma prévia do prompt (primeiros 100 caracteres).
     * Útil para logging sem expor o prompt completo.
     * 
     * @return Prévia do prompt
     */
    public String preview() {
        if (promptText.length() <= 100) {
            return promptText;
        }
        return promptText.substring(0, 100) + "...";
    }
}