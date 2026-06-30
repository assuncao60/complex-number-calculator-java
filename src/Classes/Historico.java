/*
CLASSE HISTORICO

O principal objetivo é fazer a ligação entre a memória temporária (RAM)
e o armazenamento permanente (Disco Rígido). Aloja a lista sequencial de todas as operações
(ArrayList), gere as ferramentas de leitura e escrita de ficheiros de texto (.txt) e utiliza
um algoritmo de recálculo em cascata para atualizar toda a matemática sempre que uma conta
antiga é editada.
 */

package Classes;

import java.util.ArrayList;
import java.io.File;
import java.io.FileWriter;
import java.util.Scanner;

public class Historico {
    private ArrayList<Operacao> listaOperacoes; // ArrayList seria uma "RAM elástica"
    private final String NOME_FICHEIRO = "historico.txt"; // Indicamos o caminho do disco rígido



    // --------------------------------------------------------------- Construtor
    // Função para Criar o ArrayList
    public Historico() {
        // Cria a lista vazia pronta a receber dados na RAM
        this.listaOperacoes = new ArrayList<>();

        carregarDoFicheiro();
    }


    // Função para carregar o ficheiro
    private void carregarDoFicheiro() {
        File ficheiro = new File(NOME_FICHEIRO);

        if (!ficheiro.exists()) {
            System.out.println("Aviso: Nenhum histórico encontrado. A iniciar a memória limpa.");
            return; // Sai porque não há nada para ler
        }

        try {
            // Tenta abrir o ficheiro
            Scanner leitorFicheiro = new Scanner(ficheiro);

            // Enquanto o ficheiro ainda tiver linhas por ler
            while (leitorFicheiro.hasNextLine()) {
                String linha = leitorFicheiro.nextLine(); // Lê a linha de texto

                // Corta a linha onde estão os ;
                String[] pedacos = linha.split(";");

                // ArrayList vai ter 5 pedaços de armazenamento
                if (pedacos.length == 5) {

                    // Associa o tipo de armazenamento de cada parte da operação
                    char operador = pedacos[0].charAt(0);
                    double visReal = Double.parseDouble(pedacos[1]);
                    double visImag = Double.parseDouble(pedacos[2]);
                    double resReal = Double.parseDouble(pedacos[3]);
                    double resImag = Double.parseDouble(pedacos[4]);

                    // Reconstrói os pedaços na memória RAM (utilizado no Histórico)
                    NumeroComplexo visitante = new NumeroComplexo(visReal, visImag);
                    NumeroComplexo resultado = new NumeroComplexo(resReal, resImag);

                    listaOperacoes.add(new Operacao(operador, resultado, visitante));
                }
            }

            leitorFicheiro.close(); // Fecha o leitor
            System.out.println("Histórico anterior carregado com sucesso!");

        } catch (Exception e) {
            // Caso não encontre, é criado um novo
            System.out.println("Nenhum histórico encontrado. A iniciar com a memória limpa.");
        }
    }
    // -------------------------------------------------------------------------


    // ---------------------------------------------------------- Métodos Principais
    // Adiciona uma nova conta à memória RAM (ArrayList) (A última operação feita)
    public void adicionarOperacao(Operacao novaConta) {
        listaOperacoes.add(novaConta);
    }


    // Função para guardar o ficheiro
    public void guardarFicheiro() {
        try {
            // O false significa que vai apagar o ficheiro antigo e criar um novo com a RAM atual
            FileWriter escritor = new FileWriter(NOME_FICHEIRO, false);

            // Faz um ciclo por todas as operações que estão na lista
            for (int i = 0; i < listaOperacoes.size(); i++) {
                Operacao op = listaOperacoes.get(i);

                // Escreve no ficheiro por cada posição que encontra
                escritor.write(op.getOperador() + ";" +
                        op.getVisitante().getReal() + ";" +
                        op.getVisitante().getImaginario() + ";" +
                        op.getResultado().getReal() + ";" +
                        op.getResultado().getImaginario() + "\n");
            }

            escritor.close();

        } catch (Exception e) {
            System.out.println("Erro ao tentar guardar o ficheiro no disco!");
        }
    }


    // Imprime todas as contas que estão guardadas na RAM
    public void mostrarHistorico() {
        System.out.println("\n+---+ HISTÓRICO DE OPERAÇÕES +---+");

        // Verifica se a lista está vazia
        if (listaOperacoes.isEmpty()) {
            System.out.println("Não existem operações ainda!");
            return;
        }

        // Percorre a gaveta toda e imprime
        for (int i = 0; i < listaOperacoes.size(); i++) {
            Operacao op = listaOperacoes.get(i);

            System.out.println(i + ". " + op);
        }
        System.out.println("+-----------------------------+");
    }


    // Alterar operações anteriores na RAM
    public NumeroComplexo editarOperacao(int indice, NumeroComplexo novoVisitante) {

        // Descobre o operador
        char operadorAntigo = listaOperacoes.get(indice).getOperador();

        // Atualiza com o no visitante
        // O resultado é 0 porque na Cascata vai subtitui-lo
        listaOperacoes.set(indice, new Operacao(operadorAntigo, new NumeroComplexo(0, 0), novoVisitante));

        // Comunica com a função (recalcularEfeitoCascata)
        recalcularEfeitoCascata(indice);;

        // Devolve o último resultado da lista para atualizar o visor
        return getUltimoResultado();
    }


    // Remover uma operação e recalcular tudo o que está a seguir
    public NumeroComplexo removerOperacao(int indice) {

        // O ArrayList apaga logo a posição e junta o resto
        listaOperacoes.remove(indice);

        if (listaOperacoes.isEmpty()) {return new NumeroComplexo(0, 0);}

        // Refaz as contas a partir do indice
        recalcularEfeitoCascata(indice);

        return getUltimoResultado();
    }


    // Trocar operações
    public NumeroComplexo trocarOperacoes(int indiceA, int indiceB) {

        // Apanha as linhas para fazer a troca
        Operacao operacaoA = listaOperacoes.get(indiceA);
        Operacao operacaoB = listaOperacoes.get(indiceB);

        // Inverte as operações
        listaOperacoes.set(indiceA, operacaoB);
        listaOperacoes.set(indiceB, operacaoA);

        // Descobre o ponto mais acima no ArrayList para recalcular a partir dai para baixo
        int pontoPartida = Math.min(indiceA, indiceB);
        recalcularEfeitoCascata(pontoPartida);

        return getUltimoResultado();
    }


    // Mover uma operação para outro sítio da lista
    public NumeroComplexo moverOperacao(int origem, int destino) {

        // Apaga da origem e guarda a informação que lá estava
        Operacao operacaoMovida = listaOperacoes.remove(origem);

        // Adiciona no indice de destino
        listaOperacoes.add(destino, operacaoMovida);

        // Descobre o ponto mais acima no ArrayList para recalcular a partir dai para baixo
        int pontoPartida = Math.min(origem, destino);
        recalcularEfeitoCascata(pontoPartida);

        return getUltimoResultado();
    }


    // Limpar o histórico completo
    public NumeroComplexo limparHistorico() {
        listaOperacoes.clear(); // Limpa por completo com o clear
        return new NumeroComplexo(0, 0);
    }
    // -------------------------------------------------------------------------



    // ------------------------------------------------------ Funções Auxiliares
    // Faz os cálculos de acordo com a função anterior (editarOperacao)
    // Recalcula tudo o que está para a frente da posição indicada
    private void recalcularEfeitoCascata(int indice) {

        NumeroComplexo base;

        // Descobre a base
        if(indice == 0 || listaOperacoes.isEmpty()) {
            base = new NumeroComplexo(0, 0);
        }
        else{
            base = listaOperacoes.get(indice - 1).getResultado();
        }

        for(int i = indice; i < listaOperacoes.size(); i++) {
            Operacao linhaAtual = listaOperacoes.get(i);
            char operacao = linhaAtual.getOperador();

            // Retiramos o if, pega no visitante que lá estiver na gaveta no momento
            NumeroComplexo visitante = linhaAtual.getVisitante();

            // Recalcula
            NumeroComplexo novoResultado = recalcularConta(base, operacao, visitante);

            // Atualiza a RAM pela ordem correta
            listaOperacoes.set(i, new Operacao(operacao, novoResultado, visitante));

            // Atualiza a base para o próximo ciclo
            base = novoResultado;
        }
    }


    // Função para recalcular as contas após o pedido
    private NumeroComplexo recalcularConta(NumeroComplexo base, char op, NumeroComplexo vis) {
        switch(op) {
            case '+':
                return base.soma(vis);

            case '-':
                return base.subtrair(vis);

            case '*':
                return base.multiplicar(vis);

            case '/':
                return base.dividir(vis);

            case 'p':
                return base.potencia((int) vis.getReal()); // Convertemos para int, porque é o expoente

            case 's':
                return base.simetrico();

            case 'c':
                return base.conjugado();

            case 'i':
                return base.inverso();

            default:
                return base;
        }
    }


    // Função para recuperar o último valor da ArrayList
    public NumeroComplexo getUltimoResultado() {
        if (listaOperacoes.isEmpty()) {
            return new NumeroComplexo(0, 0); // Se não houver histórico, começa a zeros
        }

        // Vai à última posição (tamanho - 1) e extrai o Resultado
        int ultimaPosicao = listaOperacoes.size() - 1;
        return listaOperacoes.get(ultimaPosicao).getResultado();
    }

    
    // Devolve o tamanho atual da lista para sabermos os limites
    public int getTamanhoHistorico() {
        return listaOperacoes.size();
    }
    // -------------------------------------------------------------------------
}
