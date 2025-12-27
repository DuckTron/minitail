package com.ministorytail.prompt.model.strategy;

public interface StyleStrategy {
    
    /**
     * Retorna as instruções específicas deste estilo narrativo
     * que serão injetadas no prompt do Gemini.
     * 
     * Estas instruções devem detalhar:
     * - Linguagem apropriada (vocabulário, estrutura de frases)
     * - Tom da narrativa (empolgante, didático, mágico, engraçado)
     * - Estrutura narrativa (como organizar a história)
     * - Elementos obrigatórios (o que cada página deve conter)
     * 
     * @return String formatada com instruções específicas do estilo
     */
    String getPromptInstructions();
    
    /**
     * Retorna o nome de exibição do estilo.
     * Usado para identificação humana e logging.
     * 
     * Exemplos: "Aventura", "Educativo", "Fantasia", "Humor"
     * 
     * @return Nome amigável do estilo
     */
    String getDisplayName();
    
    /**
     * Retorna uma descrição curta das características do estilo.
     * Usado no prompt para contextualizar o Gemini sobre o tom desejado.
     * 
     * Exemplos: 
     * - "Dinâmica, cheia de ação e descobertas emocionantes"
     * - "Clara, didática e focada no aprendizado"
     * 
     * @return Descrição curta das características
     */
    String getCharacteristics();
}