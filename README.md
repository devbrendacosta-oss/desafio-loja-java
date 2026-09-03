# Desafio Prático: Análise, Correção e Melhoria do Sistema da Loja

**Relatório Técnico de Reformulação de Software em Java**

## 1. ETAPAS 1 E 2 - ANÁLISE E TESTES DO SISTEMA ORIGINAL

O sistema original (classe MenuLoja, que integra a classe Produto) foi executado e testado navegando por todas as opções do menu, incluindo cenários fora do esperado: valores negativos, campos vazios, opções em momentos indevidos e entradas não numéricas. Os resultados de cada teste estão registrados na tabela da Etapa 3, com evidências reais de execução do programa original (sem as correções).

Além do sistema principal (MenuLoja), o pacote continha duas classes soltas `Principal.java` e `VerificarEstoque.java` cada uma com seu próprio método `main()` e seu próprio objeto Produto, sem nenhuma ligação com o restante do sistema (o `Principal.java`, inclusive, pedia o nome do produto na tela, mas nunca guardava esse valor em `produto.nome`). Como o sistema de verdade, com menu completo, é o MenuLoja, as partes úteis dessas duas classes (consultar produto e verificar estoque) já existiam ali dentro e foram apenas corrigidas; os dois arquivos soltos não fazem parte da entrega final.

## 2. ETAPA 3 - PROBLEMAS ENCONTRADOS

| Teste Realizado | Comportamento Encontrado | Comportamento Esperado | Correção? |
| :--- | :--- | :--- | :--- |
| Cadastrar produto com preço negativo (-10) | Sistema aceitou normalmente e armazenou preco = -10.0. | Sistema deveria rejeitar e pedir um novo valor. | Sim |
| Cadastrar produto com quantidade negativa (-5) | Sistema aceitou normalmente e armazenou quantidade = -5 | Sistema deveria rejeitar e pedir um novo valor. | Sim |
| Consultar produto antes de qualquer cadastro | Sistema exibiu "Nome: null", "Preço: R$ 0.0", "Quantidade: 0" como se fossem dados reais. | Sistema deveria avisar que nenhum produto foi cadastrado. | Sim |
| Cadastrar produto deixando o nome em branco | Sistema aceitou string vazia como nome (sem nenhuma verificação no código). | Sistema deveria rejeitar nome vazio. | Sim |
| Informar texto ("abc") onde esperava preço decimal | Sistema encerrou abruptamente com `java.util.InputMismatchException` (crash). | Idealmente avisaria que o valor é inválido sem travar. | Investigar |
| Escolher uma opção inexistente no menu (ex.: 9) | Sistema exibiu "Opção inválida!" corretamente (bloco default). | Mesmo comportamento já estava correto. | Não |
| Vender quantidade maior que o estoque disponível | Sistema exibiu "Estoque insuficiente!" e não alterou o estoque. | Mesmo comportamento já estava correto. | Não |
| Registrar entrada de mercadoria (reposição) | Funcionalidade não existia no sistema original. | Sistema deveria permitir aumentar o estoque de forma controlada. | Nova Opção |

## 3. ETAPA 4 - CLASSIFICAÇÃO DOS PROBLEMAS

| Problema | Classificação | Justificativa |
| :--- | :--- | :--- |
| Preço negativo aceito no cadastro | Falta de validação | O programa compila e executa normalmente, mas aceita uma informação que não deveria ser permitida pelo domínio do sistema. |
| Quantidade negativa aceita no cadastro | Falta de validação | Dado numericamente possível de digitar, mas sem sentido para o domínio de negócio (estoque negativo). |
| Nome vazio aceito no cadastro | Falta de validação | Não havia nenhuma verificação de conteúdo antes de atribuir o valor ao atributo nome. |
| Dados "fantasmas" exibidos antes do cadastro | Falta de tratamento de situação | O sistema executa uma consulta em um momento em que ela não deveria estar disponível por ausência de dados. |
| Encerramento abrupto (crash) com entrada alfabética | Falta de tratamento de situação | O erro ocorre durante a execução quando o Scanner falha ao converter texto em número. Registrado no Desafio de Investigação. |
| Ausência de opção para repor estoque | Funcionalidade incompleta | O fluxo do sistema previa apenas a saída (venda) de mercadorias, sem fluxo de entrada/reabastecimento. |

## 4. ETAPAS 5 A 8 — CORREÇÕES E MELHORIAS IMPLEMENTADAS

Todas as correções foram feitas utilizando estritamente os conteúdos definidos para esta atividade: laços `while` para repetição de entradas até a validação, estruturas `if` / `else if` / `else` para verificação de condições e `switch/case` para seleção de opções. 
### Regras de Validação e Operações
* **Cadastro de Nome:** Um laço `while` repete a pergunta enquanto a string digitada for vazia.
* **Cadastro de Preço:** Um laço `while` repete a solicitação enquanto o valor for menor ou igual a zero.
* **Cadastro de Quantidade:** Um laço `while` repete a solicitação enquanto o valor for negativo (zero é permitido).
* **Controle de Entrada de Estoque:** Criada a opção 4 (reposição), que valida via `while` para aceitar apenas quantidades estritamente maiores que zero.
* **Operações de Saída (Venda):** Valida a existência prévia de cadastro, impede vendas com quantidade nula ou negativa, verifica disponibilidade de estoque via `else if` e exibe o estado antes e depois da transação.

### Etapa 6 - Controle do Estado do Sistema
Adicionada a variável booleana `produto.cadastrado` na classe Produto. Todas as operações dependentes de um produto (consultar, verificar estoque, repor e vender) testam a condição `if (produto.cadastrado == false)` antes de prosseguir, impedindo a exibição de dados incoerentes ("dados fantasmas").

### Etapas 7 e 8 - Mensagens e Melhorias Implementadas
* **Melhoria A - Confirmação de Operação:** Exibição de um resumo (produto, quantidade, valor total) antes de finalizar a venda, solicitando confirmação (S/N) via `if/else`.
* **Melhoria B - Alerta de Estoque Baixo:** Emissão automática de aviso caso o estoque atinja 5 unidades ou menos após uma venda.
* **Melhoria C - Apresentação de Valores Calculados:** Cálculo e exibição do valor total acumulado em estoque (preço x quantidade) na consulta e valor total da transação nas vendas.
* **Melhoria E - Bloqueio de Operações Inválidas:** Interrupção imediata de comandos sobre produtos não cadastrados.
* **Melhoria F - Reorganização do Menu:** Inclusão da opção "4 - Entrada de estoque (reposição)".

### NOVOS TESTES APÓS AS CORREÇÕES

| Problema Original | Correção Realizada | Resultado Após Novo Teste |
| :--- | :--- | :--- |
| Preço negativo/zero era aceito | Laço `while` repete a leitura enquanto preco <= 0. | Valor -10 foi rejeitado com mensagem; 15.90 foi aceito com sucesso. |
| Quantidade negativa era aceita | Laço `while` repete a leitura enquanto quantidade < 0 | Valor -5 foi rejeitado; 0 e 8 foram aceitos normalmente. |
| Nome vazio era aceito | Laço `while` repete a leitura enquanto nome.equals(""). | Enter em branco foi rejeitado; "Produto Teste" foi aceito. |
| Dados "fantasmas" exibidos sem cadastro | Verificação de `produto.cadastrado == false` em todas as opções. | Opções 2, 3, 4 e 5 bloqueadas com aviso amigável antes do cadastro. |
| Venda acima do estoque com mensagem simples | Mensagem expandida com estoque disponível e solicitado via `else if`. | Solicitação de 100 un. tendo 14 em estoque detalhou a divergência claramente. |
| Sem funcionalidade de repor estoque | Implementada Opção 4 com validação de quantidade positiva via `while`. | Entrada de 0 rejeitada; entrada de 15 somou corretamente ao estoque. |
| Falta de confirmação e alertas de estoque | Confirmação pré-venda (S/N) e checagem pós-venda para estoque <= 5. | Venda solicitou confirmação; ao atingir 3 unidades, emitiu alerta visual. |

## 5. DESAFIO DE INVESTIGAÇÃO

**Cenário de Teste:** Entrada de string "abc" no campo de preço (double).

1. **O que aconteceu?** O programa foi encerrado imediatamente pelo ambiente de execução Java, sem mensagem amigável para o usuário.
2. **O programa continuou funcionando?** Não. Ocorreu o travamento total (crash) do processo JVM.
3. **Qual mensagem foi apresentada pelo Java?** `Exception in thread "main" java.util.InputMismatchException`, acompanhada do rastreamento de pilha (stack trace) da linha do `Scanner.nextDouble()`.
4. **Por que isso aconteceu?** O método `nextDouble()` tenta converter a entrada do console diretamente em ponto flutuante. Ao receber caracteres alfabéticos, a exceção é lançada. Como o código não possuía rotina de tratamento, a JVM encerrou o programa.
5. **Como tratar futuramente?** Envolvendo o bloco de leitura em uma estrutura `try/catch` para capturar a exceção e solicitar a reentrada dos dados sem encerrar a aplicação.

## 6. RELATÓRIO DO DESAFIO - DETALHAMENTO DOS PROBLEMAS

### PROBLEMA 1: Cadastro de Preço Inválido
* **Situação Testada:** Cadastro de produto com preço negativo (-10).
* **Comportamento:** Sistema aceitou o valor sem avisos e armazenou preco = -10.0.
* **Correção:** Adicionado laço `while` exigindo preco > 0.
* **Resultado:** Exibe mensagem de erro e solicita reentrada até obter valor válido.

### PROBLEMA 2: Cadastro de Quantidade Inválida
* **Situação Testada:** Cadastro de produto com quantidade negativa (-5).
* **Comportamento:** Sistema aceitou e armazenou quantidade = -5.
* **Correção:** Adicionado laço `while` impedindo valores < 0.
* **Resultado:** Recusa valores negativos e aceita zero ou números positivos.

### PROBLEMA 3: Consulta Prévia ao Cadastro
* **Situação Testada:** Executar consulta (opção 2) ao iniciar o programa.
* **Comportamento:** Exibição de valores nulos/zerados ("Nome: null", "Preço: R$ 0.0").
* **Correção:** Flag `produto.cadastrado` verificada com `if` antes de executar operações.
* **Resultado:** Exibe alerta solicitando o cadastro inicial do produto.

### PROBLEMA 4: Nome de Produto Vazio
* **Situação Testada:** Pressionar Enter sem digitar o nome no cadastro.
* **Comportamento:** Armazenamento de string vazia como nome de produto.
* **Correção:** Laço `while` repetindo a leitura enquanto o texto for vazio.
* **Resultado:** Sistema rejeita nome em branco e solicita nova digitação.

### PROBLEMA 5: Crash por Entrada Incompatível
* **Situação Testada:** Digitar "abc" no campo reservado para preço.
* **Comportamento:** Encerramento imediato da aplicação via InputMismatch Exception.
* **Correção:** Mantido sem alteração em conformidade com as diretrizes do desafio (requer `try/catch`).
* **Resultado:** Comportamento documentado para futura implementação acadêmica.

## 7. QUESTÕES PARA REFLEXÃO

1. **Um programa que executa sem apresentar erro necessariamente está correto?**
Não. O sistema original compilava e rodava, mas aceitava dados semanticamente incorretos (estoque negativo, preços zerados). A ausência de falhas de compilação não garante a correção das regras de negócio.

2. **Qual é a diferença entre um erro de sintaxe e um erro de lógica?**
Erros de sintaxe violam a gramática da linguagem e impedem a compilação. Erros de lógica permitem a compilação e execução, mas produzem resultados incoerentes com os requisitos.

3. **Por que devemos validar os dados informados pelo usuário?**
Para garantir a integridade dos dados e do fluxo da aplicação, impedindo que entradas inconsistentes comprometam cálculos posteriores ou a estabilidade do sistema.

4. **Qual foi o problema mais importante encontrado no sistema?**
O crash decorrente de entrada de texto em campos numéricos, pois interrompia totalmente o serviço e a experiência do usuário.

5. **Qual foi a correção mais difícil de implementar?**
O controle de estado global (`produto.cadastrado`), demandando a revisão de cada fluxo do `switch/case` para garantir o bloqueio consistente sem quebrar a navegação.

6. **Em quais situações você utilizou if, else if ou else?**
Nas validações condicionais do cadastro, na verificação de elegibilidade para vendas, na comparação entre demanda e estoque, na validação de cadastro existente e no controle de respostas (S/N).

7. **Qual é a função do while no sistema?**
Manter o loop principal do menu ativo e implementar estruturas de repetição para insistir na leitura de dados válidos durante os cadastros e entradas.

8. **Qual é a função do switch/case?**
Mapear a escolha do menu do usuário diretamente para a funcionalidade correspondente, mantendo o código organizado e legível.

9. **Por que devemos testar valores diferentes dos valores considerados normais?**
Porque as falhas de validação e de borda ocorrem predominantemente em cenários atípicos. O teste robusto exige a simulação de erros do usuário.

10. **Quais melhorias você implementou e por que as escolheu?**
Confirmação de vendas, alerta de estoque baixo, cálculo de saldo total, restrição de acesso pré-cadastro e inclusão da funcionalidade de reposição. Escolhidas para tornar o software completo e seguro.

11. **O que ainda poderia ser melhorado no sistema?**
Tratamento de exceções com `try/catch`, suporte a múltiplos produtos (arrays/coleções) e persistência de dados em arquivos ou bancos de dados.

## 8. ANEXO - OBSERVAÇÃO SOBRE A ESTRUTURA DO PROJETO

Os arquivos `Principal.java` e `VerificarEstoque.java`, presentes no pacote inicial, foram desconsiderados na versão final. Ambos continham métodos `main()` isolados e não integrados à classe MenuLoja. As funcionalidades de consulta e checagem de estoque foram centralizadas na arquitetura principal.


 Cadastro de Produto - Documentação

## Registro dos Testes

Com base nas situações exigidas de fronteira e valores inválidos, aqui está a tabela preenchida com os testes realizados.

| Teste | Entrada | Resultado esperado | Resultado obtido | Correto? |
| :--- | :--- | :--- | :--- | :--- |
| Estoque vazio | 0 | Sem estoque | Situação: Sem estoque | Sim |
| Estoque mínimo | 1 | Estoque baixo | Situação: Estoque baixo | Sim |
| Limite estoque baixo | 19 | Estoque baixo | Situação: Estoque baixo | Sim |
| Limite estoque suficiente | 20 | Estoque suficiente | Situação: Estoque suficiente | Sim |
| Preço negativo | -100 | Não aceitar | Laço bloqueia e pede novo valor | Sim |
| Quantidade negativa | -2 | Não aceitar | Laço bloqueia e pede novo valor | Sim |
| Qtd. de produtos negativa | -2 | Não aceitar | Encerra o programa imediatamente | Sim |

## Questões para Reflexão Resolvidas

**1. Por que um laço de repetição foi necessário nesta atividade?**
Para evitar a duplicação manual de código. O laço permite solicitar a entrada de dados (nome, preço, quantidade) e realizar os cálculos para vários produtos consecutivos de forma automatizada.

**2. Por que o `for` é adequado quando sabemos quantos produtos serão analisados?**
Porque o `for` foi projetado exatamente para iterações finitas. Como o usuário informa logo no início a quantidade de produtos, a estrutura `for` já estabelece o início, a condição de parada e o incremento na mesma linha de declaração.

**3. O que aconteceria se o incremento do `for` fosse removido?**
O programa entraria em um *loop infinito*. A variável contadora `i` nunca atingiria o limite imposto por `quantidadeProdutos`, travando a execução.

**4. Qual é a função de um contador?**
Um contador serve para registrar a frequência com que um determinado evento ocorre. No código, as variáveis como `estoqueBaixo` e `semEstoque` contam quantos produtos se enquadram em cada categoria.

**5. Qual é a função de um acumulador?**
Um acumulador serve para somar e armazenar valores progressivamente ao longo do laço. O `valorTotalEstoque` é um exemplo, pois vai somando o valor individual de cada novo produto inserido.

**6. Qual é a diferença entre contador e acumulador?**
O contador aumenta em um valor constante (geralmente `+1` a cada ocorrência), enquanto o acumulador recebe incrementos variáveis (como o preço de cada produto, que muda a cada repetição).

**7. Por que os contadores devem começar em 0?**
Porque antes do início da contagem, nenhum produto foi analisado e, logicamente, nenhum evento que se deseja contar ocorreu ainda.

**8. Por que o valor total normalmente começa em 0?**
Pois ele representa o elemento neutro da adição. Se o acumulador iniciasse com outro valor, o cálculo final estaria corrompido desde o primeiro produto.

**9. Por que o relatório final deve ficar fora do laço?**
Se o relatório estivesse dentro do laço de repetição, as informações de totalização seriam impressas na tela toda vez que um novo produto fosse adicionado, ao invés de exibir apenas o consolidado final ao término das análises.

**10. É possível utilizar `if/else` dentro de um `for`? Onde isso foi necessário?**
Sim, é perfeitamente possível e comum. Foi necessário para avaliar individualmente a quantidade em estoque de cada produto (definindo se era suficiente, baixo ou sem estoque) e para o Desafio Avançado (comparar se o valor atual era maior que o `maiorValor` já registrado).

**11. Qual melhoria você implementou?**
Foram implementadas validações com a estrutura `while` que impedem o usuário de digitar preços ou quantidades negativas (Testes Inválidos), garantindo a integridade dos dados e cálculos.

**12. Qual foi a maior dificuldade encontrada?**
A maior dificuldade foi gerenciar o *buffer* do `Scanner`. Após ler a quantidade de produtos com `nextInt()`, foi necessário adicionar um `entrada.nextLine()` vazio para evitar que o "Nome" do primeiro produto fosse pulado na leitura subsequente.
