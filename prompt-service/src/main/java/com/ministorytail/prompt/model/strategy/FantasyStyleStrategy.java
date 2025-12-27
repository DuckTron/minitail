package com.ministorytail.prompt.model.strategy;

import org.springframework.stereotype.Component;

@Component
public class FantasyStyleStrategy implements StyleStrategy {

    @Override
    public String getPromptInstructions() {
        return """
                INSTRUÇÕES DE ESTILO - FANTASIA:

                Linguagem:
                - Poética e encantadora
                - Use metáforas e imagens sensoriais
                - Vocabulário mágico (brilho, encanto, maravilha)

                Tom:
                - Místico e encantador
                - Crie atmosfera onírica
                - Desperte a imaginação

                Estrutura narrativa:
                - Construa mundos imaginários ricos
                - Inclua elementos mágicos/fantásticos
                - Deixe espaço para a imaginação da criança

                Elementos obrigatórios:
                - Pelo menos um elemento mágico/fantástico por página
                - Descrições visuais de ambientes fantásticos
                - Criaturas/personagens imaginários
                """;
    }

    @Override
    public String getDisplayName() {
        return "Fantasia";
    }

    @Override
    public String getCharacteristics() {
        return "Mágica, encantadora e repleta de imaginação";
    }
}