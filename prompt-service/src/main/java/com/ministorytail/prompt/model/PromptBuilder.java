package com.ministorytail.prompt.model;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.ministorytail.prompt.model.exceptions.InvalidPromptRequestException;
import com.ministorytail.prompt.model.strategy.AdventureStyleStrategy;
import com.ministorytail.prompt.model.strategy.EducationalStyleStrategy;
import com.ministorytail.prompt.model.strategy.FantasyStyleStrategy;
import com.ministorytail.prompt.model.strategy.HumorStyleStrategy;
import com.ministorytail.prompt.model.strategy.StyleStrategy;

@Service
public class PromptBuilder {

    private final Map<NarrativeStyle, StyleStrategy> strategies;

    public PromptBuilder(
            AdventureStyleStrategy adventure,
            EducationalStyleStrategy educational,
            FantasyStyleStrategy fantasy,
            HumorStyleStrategy humor) {

        this.strategies = Map.of(
                NarrativeStyle.ADVENTURE, adventure,
                NarrativeStyle.EDUCATIONAL, educational,
                NarrativeStyle.FANTASY, fantasy,
                NarrativeStyle.HUMOR, humor);
    }

    public FormattedPrompt build(PromptRequest request) {
        StyleStrategy strategy = strategies.get(request.getStyle());

        if (strategy == null) {
            throw new InvalidPromptRequestException(
                    "Estilo narrativo inválido: " + request.getStyle());
        }

        String promptText = buildPromptText(request, strategy);
        return FormattedPrompt.create(promptText);
    }

    private String buildPromptText(PromptRequest request, StyleStrategy strategy) {
        return String.format("""
                Você é um contador de histórias infantis especializado.

                PERFIL DA CRIANÇA:
                - Nome: %s
                - Idade: %d anos
                - Interesse principal: %s

                REQUISITOS DA HISTÓRIA:
                - Tema/Lição: %s
                - Estilo narrativo: %s (%s)
                - Número de páginas: %d

                %s

                FORMATO DE SAÍDA (JSON obrigatório):
                [
                  {
                    "nome_da_historia": "",
                    "numero_da_pagina": 1,
                    "conteudo_da_pagina": "",
                    "palavras_de_destaque_do_texto": [],
                    "prompt_da_imagem": ""
                  }
                ]

                REGRAS GERAIS:
                1. Criar exatamente %d páginas
                2. Cada página deve ter entre 50-150 palavras
                3. palavras_de_destaque_do_texto: máximo 5 palavras-chave por página
                4. prompt_da_imagem: descrição detalhada para geração de imagem (cenário, personagem, ação)
                5. Incorporar "%s" de forma natural na história
                6. Ensinar "%s" de forma %s
                7. Linguagem apropriada para idade %d

                Retorne APENAS o JSON, sem markdown ou explicações.
                """,
                request.getChildName(),
                request.getChildAge(),
                request.getSelectedInterest(),
                request.getTheme(),
                strategy.getDisplayName(),
                strategy.getCharacteristics(),
                request.getNumberOfPages(),
                strategy.getPromptInstructions(),
                request.getNumberOfPages(),
                request.getSelectedInterest(),
                request.getTheme(),
                strategy.getCharacteristics(),
                request.getChildAge());
    }
}