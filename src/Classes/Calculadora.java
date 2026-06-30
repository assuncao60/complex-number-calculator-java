/*
CLASSE CALCULADORA

O principal objetivo é fazer a ponte interativa entre o utilizador humano
e o motor matemático (NumeroComplexo). Aloja o ciclo de execução do programa
(Loop), gere as ferramentas de leitura do teclado e mantém o estado atual
da conta no "visor". Utiliza um sistema de switch-case para interpretar os
comandos.
 */

package Classes;

import java.util.Scanner;

public class Calculadora {
    private Scanner teclado; // Lê o que o utilizador escreve
    private NumeroComplexo visor; // Guarda o número atual
    private Historico historico; // Vamos buscar o Histórico à outra Classe



    // -------------------------------------------------------------------------
    // Construtor
    public Calculadora() {
        this.teclado = new Scanner(System.in); // Scanner é inicializado
        this.visor = new NumeroComplexo(0, 0); // Visor arranca do 0, para não ter lixo de memória
        this.historico = new Historico(); // Vai buscar o histórico
        this.visor = historico.getUltimoResultado(); // Recupera o último valor do ArrayList para iniciar com ele
    }
    // -------------------------------------------------------------------------



    // ------------------------------------------------------ Funções Auxiliares
    // Menu
    private void mostrarMenu() {
        System.out.println("\n+-----------------------------+");
        System.out.println("|     CALCULADORA COMPLEXA    |");
        System.out.println("+-----------------------------+");
        System.out.println(" [+] Somar");
        System.out.println(" [-] Subtrair");
        System.out.println(" [*] Multiplicar");
        System.out.println(" [/] Dividir");
        System.out.println(" [p] Potência");
        System.out.println(" [s] Simétrico");
        System.out.println(" [c] Conjugado");
        System.out.println(" [i] Inverso");
        System.out.println(" [h] Ver Histórico");
        System.out.println(" [e] Editar Operação");
        System.out.println(" [a] Mostrar Ajuda");
        System.out.println(" [g] Guardar Ficheiro");
        System.out.println(" [f] Fechar Programa");

        System.out.println("+-----------------------------+");
        System.out.print("Escolha a operação: ");
    }


    //Mostrar um subMenu no Editar Operação
    private void mostrarSubMenuEditar() {
        historico.mostrarHistorico();

        System.out.println("\n+----+ GESTÃO DE HISTÓRICO +----+");
        System.out.println(" 1. Editar Visitante de uma Operação");
        System.out.println(" 2. Remover uma Operação");
        System.out.println(" 3. Mover uma Operação");
        System.out.println(" 4. Trocar a ordem de duas Operações");
        System.out.println(" 5. Limpar TODO o Histórico");
        System.out.println(" 0. Cancelar / Voltar");
        System.out.println("+--------------------------------+");
        System.out.print("\nEscolha a opção: ");

        int opcaoHist = teclado.nextInt();

        // Método de segurança para que não existam interações com o Histórico Vazio
        if (historico.getTamanhoHistorico() == 0 && opcaoHist >= 1 && opcaoHist <= 4) {
            System.out.println("Erro: O histórico está vazio! Não há operações para gerir.");
            return; // Corta a execução aqui e volta ao menu principal
        }

        switch (opcaoHist) {
            case 1:
                int indEditar = pedirIndiceValido("Qual o índice da operação a editar? ");

                System.out.println("Introduza os novos valores para o visitante:");
                NumeroComplexo novoVis = pedirNovoNumero();

                visor = historico.editarOperacao(indEditar, novoVis);
                System.out.println("Operação editada e histórico recalculado!");
                break;

            case 2:
                int indRemover = pedirIndiceValido("Qual o índice da operação a remover? ");

                visor = historico.removerOperacao(indRemover);

                System.out.println("Operação removida e histórico recalculado!");
                break;

            case 3:
                int indOrigem = pedirIndiceValido("Qual o índice da operação que quer mover? ");
                int indDestino = pedirIndiceValido("Para que novo índice a quer mover? ");

                visor = historico.moverOperacao(indOrigem, indDestino);

                System.out.println("Operação movida e histórico recalculado!");
                break;

            case 4:
                int indA = pedirIndiceValido("Índice da primeira operação: ");
                int indB = pedirIndiceValido("Índice da segunda operação: ");

                visor = historico.trocarOperacoes(indA, indB);

                System.out.println("Operações trocadas e histórico recalculado!");
                break;

            case 5:
                System.out.println("A apagar todo o histórico...");
                visor = historico.limparHistorico();

                System.out.println("Histórico limpo! Visor regressou a zero.");
                break;

            case 0:
                System.out.println("A voltar ao menu principal...");
                break;

            default:
                System.out.println("Opção de gestão inválida!");
                break;
        }
    }


    // Mostrar as instruções (Case 'a' -> para pedir ajuda)
    private void mostrarAjuda() {
        System.out.println("\n+----------------+ AJUDA +----------------+");
        System.out.println(" SINTAXE DAS OPERAÇÕES:");
        System.out.println("  [+] [-] [*] [/] -> Pede a parte Real e Imaginária do visitante.");
        System.out.println("  [p] -> Pede um número inteiro para o expoente.");
        System.out.println("  [s] [c] [i] -> Calculam instantaneamente usando o valor do Visor.");
        System.out.println("  [e] -> Abre a gestão avançada para editar/remover o histórico.");
        System.out.println("  [h] -> Imprime todas as contas feitas até agora.");
        System.out.println("  [g] -> Guarda manualmente o histórico no ficheiro txt.");
        System.out.println("  [f] -> Encerra a calculadora.");
        System.out.println("+-----------------------------------------+");
    }


    // Pedir os Números para o Cálculo
    private NumeroComplexo pedirNovoNumero() {
        while (true) {
            try {
                System.out.print("Digite a parte Real: ");
                double realDigitado = teclado.nextDouble();

                System.out.print("Digite a parte Imaginária: ");
                double imaginarioDigitado = teclado.nextDouble();

                // Devolve o número e sai do ciclo (se não rebentar antes devido a algo mal introduzido)
                return new NumeroComplexo(realDigitado, imaginarioDigitado);

            } catch (Exception e) {
                // Caso não chegue ao passo anterior
                System.out.println("Erro: Por favor, digite apenas números válidos (use a vírgula para decimais).");

                // Limpar a letra que ficou no buffer
                teclado.nextLine();
            }
        }
    }


    // Pedir o Expoente
    private int pedirExpoente() {
        while (true) {
            try {
                System.out.print("Digite o Expoente (Número Inteiro): ");
                int expoente = teclado.nextInt();

                return expoente; // Se for inteiro, sai e devolve

            } catch (Exception e) {
                System.out.println("Erro: O expoente tem de ser um número inteiro, sem letras nem casas decimais!");
                teclado.nextLine(); // Limpa o buffer
            }
        }
    }
    // -------------------------------------------------------------------------



    // --------------------------------------------------------- Métodos de Ação
    public void iniciar() {

        // Verifica se a calculadora está a trabalhar
        boolean aTrabalhar = true;

        // Ciclo infinito
        while (aTrabalhar) {

            System.out.println("\nVisor atual: " + visor);

            mostrarMenu(); // Chama a função MENU
            char opcao = teclado.next().charAt(0); // Lê apenas uma letra/número

            switch(opcao) {
                case '+':
                    // Guarda os números do utilizador
                    NumeroComplexo visSoma = pedirNovoNumero();
                    // Guarda a operação feita
                    visor = visor.soma(visSoma); // Guarda de volta para o resultado não se perder

                    historico.adicionarOperacao(new Operacao('+', visor, visSoma));

                    break;

                case '-':
                    NumeroComplexo visSubtrair = pedirNovoNumero();
                    visor = visor.subtrair(visSubtrair);

                    historico.adicionarOperacao(new Operacao('-', visor, visSubtrair));

                    break;

                case '*':
                    NumeroComplexo visMultiplicar = pedirNovoNumero();
                    visor = visor.multiplicar(visMultiplicar);

                    historico.adicionarOperacao(new Operacao('*', visor, visMultiplicar));

                    break;

                case '/':
                    NumeroComplexo visDividir = pedirNovoNumero();

                    // Não é possível dividir por zeros
                    if(visDividir.getReal() == 0 && visDividir.getImaginario() == 0) {
                        System.out.println("Erro: Não é possível dividir por 0");
                        break;
                    }

                    visor = visor.dividir(visDividir);

                    historico.adicionarOperacao(new Operacao('/', visor, visDividir));

                    break;

                case 'p':
                    int expoente = pedirExpoente();
                    visor = visor.potencia(expoente);

                    // Expoente vai assumir como número complexo
                    NumeroComplexo visPotencia = new NumeroComplexo(expoente, 0);
                    historico.adicionarOperacao(new Operacao('p', visor, visPotencia));

                    break;

                case 's':
                    visor = visor.simetrico();
                    historico.adicionarOperacao(new Operacao('s', visor, new NumeroComplexo(0, 0)));

                    break;

                case 'c':
                    visor = visor.conjugado();
                    historico.adicionarOperacao(new Operacao('c', visor, new NumeroComplexo(0, 0)));

                    break;

                case 'i':

                    // Não deixa fazer se o visor estiver a 0 (o inverso de 0 dá erro)
                    if(visor.getReal() == 0 && visor.getImaginario() == 0) {
                        System.out.println("Erro: Não é possível calcular o inverso de zero");
                        break;
                    }

                    visor = visor.inverso();
                    historico.adicionarOperacao(new Operacao('i', visor, new NumeroComplexo(0, 0)));

                    break;

                case 'h':
                    historico.mostrarHistorico();;
                    break;

                case 'e':
                    mostrarSubMenuEditar();
                    break;

                case 'a':
                    mostrarAjuda();
                    break;

                case 'g':
                    historico.guardarFicheiro();
                    System.out.println("Histórico guardado com sucesso!");
                    break;

                case 'f':
                    System.out.println("A fecha programa... Até à próxima!");
                    aTrabalhar = false; // Corta o while
                    break;

                default:
                    System.out.println("Opção inválida!");
                    break;
            }

        }
    }


    // Pedir um Índice Válido (Protege contra letras e números fora da lista)
    private int pedirIndiceValido(String mensagem) {
        while(true) {
            try{
                System.out.print(mensagem);
                int indice = teclado.nextInt();

                int tamanhoMaximo = historico.getTamanhoHistorico();

                // Valor tem de ser entre 0 e Max
                if(indice >= 0 && indice < tamanhoMaximo) {
                    return indice;
                }
                else{
                    System.out.println("Erro: Esse índice não existe! (Valores válidos: 0 a " + (tamanhoMaximo - 1) + ")");
                }
            } catch (Exception e) {
                System.out.println("Erro: Por favor, digite apenas números inteiros.");
                teclado.nextLine(); // Limpa o lixo do buffer
            }
        }
    }
    // -------------------------------------------------------------------------
}
