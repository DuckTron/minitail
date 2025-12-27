package com.ministorytail.prompt.model.strategy;

import org.springframework.stereotype.Component;

@Component
public class AdventureStyleStrategy implements StyleStrategy {

    @Override
    public String getPromptInstructions() {
        return """
                INSTRUÇÕES DE ESTILO - AVENTURA:

                Linguagem:
                - Use verbos de ação (correr, saltar, explorar, descobrir)
                - Frases curtas e dinâmicas
                - Vocabulário empolgante (desafio, coragem, mistério)

                Tom:
                - Empolgante e corajoso
                - Crie tensão e resolução
                - Incentive a curiosidade e bravura

                Estrutura narrativa:
                - Apresente desafios que o protagonista deve superar
                - Inclua momentos de descoberta
                - Finalize com sensação de conquista

                Elementos obrigatórios:
                - Pelo menos um desafio/obstáculo por página
                - Descrições sensoriais de ambientes (sons, cores, texturas)
                """;
    }

    @Override
    public String getDisplayName() {
        return "Aventura";
    }

    @Override
    public String getCharacteristics() {
        return "Dinâmica, cheia de ação e descobertas emocionantes";
    }
}