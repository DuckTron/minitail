package com.ministorytail.prompt.model.exceptions;

/**
 * Exceção lançada quando um PromptRequest viola regras de negócio.
 * 
 * Esta é uma exceção de domínio (não técnica) que indica que os dados
 * fornecidos não atendem aos critérios para criar um prompt válido.
 * 
 * Exemplos:
 * - Nome da criança vazio
 * - Idade fora do intervalo 3-12
 * - Número de páginas inválido
 * - Interesse selecionado vazio
 */
public class InvalidPromptRequestException extends RuntimeException {

    /**
     * Construtor que recebe a mensagem de erro.
     * 
     * @param message Descrição específica da violação de regra de negócio
     */
    public InvalidPromptRequestException(String message) {
        super(message);
    }

    /**
     * Construtor que recebe mensagem e causa raiz.
     * 
     * @param message Descrição da violação
     * @param cause   Exceção que originou este erro
     */
    public InvalidPromptRequestException(String message, Throwable cause) {
        super(message, cause);
    }
}