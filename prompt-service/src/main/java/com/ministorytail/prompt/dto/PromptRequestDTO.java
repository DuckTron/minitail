package com.ministorytail.prompt.dto;

import com.ministorytail.prompt.model.NarrativeStyle;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PromptRequestDTO {

    @NotBlank(message = "Nome da criança é obrigatório")
    @Size(max = 50, message = "Nome deve ter no máximo 50 caracteres")
    private String childName;

    @NotNull(message = "Idade é obrigatória")
    @Min(value = 3, message = "Idade mínima é 3 anos")
    @Max(value = 12, message = "Idade máxima é 12 anos")
    private Integer childAge;

    @NotBlank(message = "Interesse é obrigatório")
    @Size(min = 2, max = 30, message = "Interesse deve ter entre 2 e 30 caracteres")
    private String selectedInterest;

    @NotBlank(message = "Tema é obrigatório")
    @Size(min = 5, max = 100, message = "Tema deve ter entre 5 e 100 caracteres")
    private String theme;

    @NotNull(message = "Estilo narrativo é obrigatório")
    private NarrativeStyle style;

    @NotNull(message = "Número de páginas é obrigatório")
    @Min(value = 1, message = "Mínimo 1 página")
    @Max(value = 10, message = "Máximo 10 páginas")
    private Integer numberOfPages;
}