package br.edu.aula;

import java.util.Scanner;

/**
 * Problemas encontrados na versão original:
 * 1) ERRO DE LÓGICA GRAVE: o programa exibia "Nome do produto: " na tela,
 *    mas nunca chamava entrada.nextLine() para guardar o valor digitado em
 *    produto.nome. Ou seja, o nome digitado pelo usuário era descartado, e
 *    o produto ficava sempre com nome = null.
 * 2) O preço podia ser zero ou negativo (nenhuma validação existia).
 * 3) A quantidade podia ser negativa (nenhuma validação existia).
 *
 * Correção: foi adicionada a leitura do nome que faltava, e laços "while"
 * repetem cada pergunta enquanto o valor digitado não for válido, usando
 * apenas if/else — sem métodos extras e sem tratamento de exceções
 * (try/catch), pois esses assuntos não fazem parte do conteúdo desta
 * atividade.
 */
public class Principal {

    public static void main(String[] args) {

        Scanner entrada = new Scanner(System.in);

        Produto produto = new Produto();

        System.out.println("=== CADASTRO DE PRODUTO ===");

        // Correção: o nome digitado agora é realmente lido e guardado,
        // e não é mais aceito em branco.
        String nome = "";
        while (nome.equals("")) {
            System.out.print("Nome do produto: ");
            nome = entrada.nextLine();
            if (nome.equals("")) {
                System.out.println("O nome não pode ficar vazio. Digite novamente.");
            }
        }
        produto.nome = nome;

        // Correção: preço zero ou negativo não é mais aceito.
        double preco = -1;
        while (preco <= 0) {
            System.out.print("Preço do produto: R$ ");
            preco = entrada.nextDouble();
            entrada.nextLine();
            if (preco <= 0) {
                System.out.println("O preço deve ser maior que zero. Digite novamente.");
            }
        }
        produto.preco = preco;

        // Correção: quantidade negativa não é mais aceita.
        int quantidade = -1;
        while (quantidade < 0) {
            System.out.print("Quantidade: ");
            quantidade = entrada.nextInt();
            entrada.nextLine();
            if (quantidade < 0) {
                System.out.println("A quantidade não pode ser negativa. Digite novamente.");
            }
        }
        produto.quantidade = quantidade;
        produto.cadastrado = true;

        System.out.println("\n=== PRODUTO CADASTRADO ===");
        System.out.println("Nome: " + produto.nome);
        System.out.println("Preço: R$ " + produto.preco);
        System.out.println("Quantidade: " + produto.quantidade);

        double valorTotal = produto.preco * produto.quantidade;
        System.out.println("Valor total em estoque: R$ " + valorTotal);

        entrada.close();
    }
}
