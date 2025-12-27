package com.ministorytail.prompt.model;

/**
 * Enum que representa os estilos narrativos disponíveis para geração de
 * histórias.
 * 
 * Este é um Value Object que encapsula:
 * - Identificador técnico (nome do enum)
 * - Nome de exibição (para UI)
 * - Características do estilo (descrição curta)
 * 
 * Nota: As instruções específicas de cada estilo estão nas classes Strategy
 * correspondentes.
 */
public enum NarrativeStyle {

    ADVENTURE(
            "Aventura",
            "Dinâmica, cheia de ação e descobertas emocionantes"),

    EDUCATIONAL(
            "Educativo",
            "Clara, didática e focada no aprendizado"),

    FANTASY(
            "Fantasia",
            "Mágica, encantadora e repleta de imaginação"),

    HUMOR(
            "Humor",
            "Divertida, leve e com momentos engraçados");

    private final String displayName;
    private final String characteristics;

    /**
     * Construtor privado do enum.
     * 
     * @param displayName     Nome amigável para exibição
     * @param characteristics Descrição curta das características do estilo
     */
    NarrativeStyle(String displayName, String characteristics) {
        this.displayName = displayName;
        this.characteristics = characteristics;
    }

    /**
     * Retorna o nome de exibição do estilo.
     * 
     * @return Nome amigável (ex: "Aventura")
     */
    public String getDisplayName() {
        return displayName;
    }

    /**
     * Retorna as características do estilo.
     * 
     * @return Descrição curta (ex: "Dinâmica, cheia de ação...")
     */
    public String getCharacteristics() {
        return characteristics;
    }
}