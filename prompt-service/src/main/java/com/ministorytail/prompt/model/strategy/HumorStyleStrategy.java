package com.ministorytail.prompt.model.strategy;

import org.springframework.stereotype.Component;

@Component
public class HumorStyleStrategy implements StyleStrategy {

    @Override
    public String getPromptInstructions() {
        return """
                INSTRUÇÕES DE ESTILO - HUMOR:

                Linguagem:
                - Leve e divertida
                - Use trocadilhos apropriados para a idade
                - Palavras engraçadas e sons cômicos

                Tom:
                - Descontraído e alegre
                - Crie situações inusitadas
                - Evite humor que diminua personagens

                Estrutura narrativa:
                - Situações cômicas que ensinam
                - Reviravoltas inesperadas e engraçadas
                - Final feliz e divertido

                Elementos obrigatórios:
                - Pelo menos um momento cômico por página
                - Situações absurdas mas seguras
                - Humor visual (para as imagens)
                """;
    }

    @Override
    public String getDisplayName() {
        return "Humor";
    }

    @Override
    public String getCharacteristics() {
        return "Divertida, leve e com momentos engraçados";
    }
}