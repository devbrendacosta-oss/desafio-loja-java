package br.edu.aula;

/**
 * Classe Produto
 *
 * Mantida simples, do mesmo jeito que já vinha sendo usada nas aulas:
 * apenas atributos públicos, sem métodos.
 *
 * A única mudança foi acrescentar o atributo "cadastrado", do tipo boolean,
 * exatamente como sugerido no próprio enunciado da Etapa 6, para o sistema
 * conseguir saber se já existe ou não um produto cadastrado.
 */
public class Produto {
    String nome;
    double preco;
    int quantidade;
    boolean cadastrado;
}
