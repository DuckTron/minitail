package com.ministorytail.prompt.model.strategy;

import org.springframework.stereotype.Component;

@Component
public class EducationalStyleStrategy implements StyleStrategy {

    @Override
    public String getPromptInstructions() {
        return """
                INSTRUÇÕES DE ESTILO - EDUCATIVO:

                Linguagem:
                - Clara, simples e direta
                - Explique conceitos com analogias do cotidiano
                - Use perguntas retóricas para engajar

                Tom:
                - Instrutivo mas amigável
                - Encorajador e positivo
                - Paciente e acolhedor

                Estrutura narrativa:
                - Introduza um conceito por vez
                - Dê exemplos concretos
                - Reforce o aprendizado ao final

                Elementos obrigatórios:
                - Explicações graduais (do simples ao complexo)
                - Conexões com o mundo real da criança
                - Recapitulação do aprendizado na última página
                """;
    }

    @Override
    public String getDisplayName() {
        return "Educativo";
    }

    @Override
    public String getCharacteristics() {
        return "Clara, didática e focada no aprendizado";
    }
}