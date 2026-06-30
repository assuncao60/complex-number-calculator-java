/*
CLASSE OPERACAO

Funciona como um bloco de memória temporária (semelhante a uma struct em C).
O seu único objetivo é guardar a "fotografia" de uma conta realizada
(o símbolo do operador e o valor do resultado). Serve como base de transporte
de dados para a construção do Histórico da calculadora, permitindo a extração
de informações exclusivamente através de métodos Get.
 */

package Classes;

public class Operacao {
    private char operador; // Guarda a letra ou símbolo da conta que foi feita
    private NumeroComplexo resultado; // Guarda o objeto completo com o valor final que saiu dessa conta
    private NumeroComplexo visitante; // Memoriza o que o utilizador digitou



    // -------------------------------------------------------------------------
    // Construtor
    public Operacao(char operador, NumeroComplexo resultado, NumeroComplexo visitante) {
        this.operador = operador;
        this.resultado = resultado;
        this.visitante = visitante;
    }
    // -------------------------------------------------------------------------



    // ---------------------------------------------------------- Métodos Básicos
    // Get (variáveis private)
    public char getOperador() {return operador;}
    public NumeroComplexo getResultado() {return resultado;}
    public NumeroComplexo getVisitante() {return visitante;}


    // ToString (para imprimir no ecrã)
    @Override //Avisa o Java que estou a reescrever a forma padrão usando a minha própria lógica
    public String toString() {
        // Exemplo: Operação: [+] com (5.0+2.0i) -> Resultado: 15.0+2.0i
        return "Operação: [" + operador + "] com (" + visitante + ") -> Resultado: " + resultado;
    }
    // -------------------------------------------------------------------------
}
