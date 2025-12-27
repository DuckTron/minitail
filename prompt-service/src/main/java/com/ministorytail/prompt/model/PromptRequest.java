package com.ministorytail.prompt.model;

import com.ministorytail.prompt.model.exceptions.InvalidPromptRequestException;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Value Object que representa uma solicitação de prompt para o Gemini.
 * 
 * Invariantes (regras de negócio):
 * - childName: 1-50 caracteres (trimmed)
 * - childAge: 3-12 anos
 * - selectedInterest: 2-30 caracteres, não vazio
 * - theme: 5-100 caracteres
 * - style: não null
 * - numberOfPages: 1-10
 * 
 * Este objeto é imutável e só pode ser criado via factory method,
 * garantindo que sempre esteja em estado válido.
 */
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE) // Construtor privado
public class PromptRequest {

    private final String childName;
    private final Integer childAge;
    private final String selectedInterest;
    private final String theme;
    private final NarrativeStyle style;
    private final Integer numberOfPages;

    /**
     * Factory method para criar um PromptRequest válido.
     * 
     * Valida todas as regras de negócio antes de criar o objeto.
     * Se alguma regra for violada, lança InvalidPromptRequestException.
     * 
     * @param childName        Nome da criança
     * @param childAge         Idade da criança
     * @param selectedInterest Interesse escolhido para a história
     * @param theme            Tema/lição a ser ensinada
     * @param style            Estilo narrativo
     * @param numberOfPages    Número de páginas da história
     * @return PromptRequest válido
     * @throws InvalidPromptRequestException se alguma regra for violada
     */
    public static PromptRequest create(
            String childName,
            Integer childAge,
            String selectedInterest,
            String theme,
            NarrativeStyle style,
            Integer numberOfPages) {

        // Valida cada campo (ordem: do mais crítico ao menos crítico)
        validateChildName(childName);
        validateChildAge(childAge);
        validateSelectedInterest(selectedInterest);
        validateTheme(theme);
        validateStyle(style);
        validateNumberOfPages(numberOfPages);

        // Se passou por todas as validações, cria o objeto
        return new PromptRequest(
                childName.trim(),
                childAge,
                selectedInterest.trim(),
                theme.trim(),
                style,
                numberOfPages);
    }

    // ===== VALIDAÇÕES PRIVADAS =====

    /**
     * Valida o nome da criança.
     * Regra: deve ter entre 1 e 50 caracteres (após trim).
     */
    private static void validateChildName(String childName) {
        if (childName == null || childName.trim().isEmpty()) {
            throw new InvalidPromptRequestException(
                    "Nome da criança é obrigatório");
        }

        String trimmedName = childName.trim();
        if (trimmedName.length() > 50) {
            throw new InvalidPromptRequestException(
                    "Nome da criança deve ter no máximo 50 caracteres");
        }
    }

    /**
     * Valida a idade da criança.
     * Regra: deve estar entre 3 e 12 anos (público-alvo de histórias infantis).
     */
    private static void validateChildAge(Integer childAge) {
        if (childAge == null) {
            throw new InvalidPromptRequestException(
                    "Idade da criança é obrigatória");
        }

        if (childAge < 3 || childAge > 12) {
            throw new InvalidPromptRequestException(
                    "Idade da criança deve estar entre 3 e 12 anos");
        }
    }

    /**
     * Valida o interesse selecionado.
     * Regra: deve ter entre 2 e 30 caracteres (após trim).
     */
    private static void validateSelectedInterest(String selectedInterest) {
        if (selectedInterest == null || selectedInterest.trim().isEmpty()) {
            throw new InvalidPromptRequestException(
                    "Interesse selecionado é obrigatório");
        }

        String trimmedInterest = selectedInterest.trim();
        if (trimmedInterest.length() < 2) {
            throw new InvalidPromptRequestException(
                    "Interesse selecionado deve ter no mínimo 2 caracteres");
        }

        if (trimmedInterest.length() > 30) {
            throw new InvalidPromptRequestException(
                    "Interesse selecionado deve ter no máximo 30 caracteres");
        }
    }

    /**
     * Valida o tema/lição da história.
     * Regra: deve ter entre 5 e 100 caracteres (após trim).
     */
    private static void validateTheme(String theme) {
        if (theme == null || theme.trim().isEmpty()) {
            throw new InvalidPromptRequestException(
                    "Tema da história é obrigatório");
        }

        String trimmedTheme = theme.trim();
        if (trimmedTheme.length() < 5) {
            throw new InvalidPromptRequestException(
                    "Tema deve ter no mínimo 5 caracteres");
        }

        if (trimmedTheme.length() > 100) {
            throw new InvalidPromptRequestException(
                    "Tema deve ter no máximo 100 caracteres");
        }
    }

    /**
     * Valida o estilo narrativo.
     * Regra: não pode ser null.
     */
    private static void validateStyle(NarrativeStyle style) {
        if (style == null) {
            throw new InvalidPromptRequestException(
                    "Estilo narrativo é obrigatório");
        }
    }

    /**
     * Valida o número de páginas.
     * Regra: deve estar entre 1 e 10 (limite técnico e de custo de API).
     */
    private static void validateNumberOfPages(Integer numberOfPages) {
        if (numberOfPages == null) {
            throw new InvalidPromptRequestException(
                    "Número de páginas é obrigatório");
        }

        if (numberOfPages < 1 || numberOfPages > 10) {
            throw new InvalidPromptRequestException(
                    "Número de páginas deve estar entre 1 e 10");
        }
    }
}