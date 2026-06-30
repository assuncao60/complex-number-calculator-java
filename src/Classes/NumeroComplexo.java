/*
CLASSE NUMERO COMPLEXO

Seria como se fosse o motor matemático da calculadora. Funciona como o molde
de um número complexo, guardando apenas o valor atual do ecrã (parte real e imaginária).
Contém toda a lógica das operações matemáticas, fazendo os cálculos de forma isolada
ou recebendo um segundo número "visitante" para as operações compostas, devolvendo sempre
um novo resultado.
 */

package Classes;

import java.lang.Math;

public class NumeroComplexo {
    private double real; // A
    private double imaginario; // B



    // -------------------------------------------------------------------------
    // Construtor (para criar a operação)
    public NumeroComplexo(double real, double imaginario) {
        this.real = real;
        this.imaginario = imaginario;
    }
    // -------------------------------------------------------------------------



    // ---------------------------------------------------------- Métodos Básicos
    // Get e Set (variáveis private)
    public void setReal(double real) {this.real = real;}
    public double getReal() {return real;}

    public void setImaginario(double imaginario) {this.imaginario = imaginario;}
    public double getImaginario() {return imaginario;}


    // ToString (para imprimir no ecrã)
    @Override //Avisa o Java que estou a reescrever a forma padrão usando a minha própria lógica
    public String toString() {
        // Se for >= 0, sabe automaticamente que o número é positivo, implementa o +
        if (imaginario >= 0) {
            return real + " + " + imaginario + "i";
        }
        else {
            return real + " - " + Math.abs(imaginario) + "i"; // Tira o sinal Math.abs
        }
    }


    // Compara se dois números complexos são exatamente iguais
    public boolean equals(NumeroComplexo outro) {
        if (outro == null) { // Verificação de Segurança
            return false;
        }

        // a = c E b = d
        if (this.real == outro.real && this.imaginario == outro.imaginario) {
            return true;
        } else {
            return false;
        }
    }
    // -------------------------------------------------------------------------



    // ----------------------------------------------------- Métodos Matemáticos
    // Soma
    public NumeroComplexo soma(NumeroComplexo w) {

        double novoReal = this.real + w.real;
        double novoImaginario = this.imaginario + w.imaginario;

        return new NumeroComplexo(novoReal, novoImaginario);
    }


    // Subtração
    public NumeroComplexo subtrair(NumeroComplexo w) {

        double novoReal = this.real - w.real;
        double novoImaginario = this.imaginario - w.imaginario;

        return new NumeroComplexo(novoReal, novoImaginario);
    }


    // Multiplicação
    public NumeroComplexo multiplicar(NumeroComplexo w) {
        // (ac - bd)
        double novoReal = (this.real * w.real) - (this.imaginario * w.imaginario);
        // (ad + bc)
        double novoImaginario = (this.real * w.imaginario) + (this.imaginario * w.real);

        return new NumeroComplexo(novoReal, novoImaginario);
    }


    // Divisão
    public NumeroComplexo dividir(NumeroComplexo w) {
        // Atual (this) e multiplicamos pelo inverso (w)
        return this.multiplicar(w.inverso());
    }


    // Potencia
    public NumeroComplexo potencia(int n) {

        // Resultado assume duas posições, 1 e 0i, de acordo como é feita a multiplicação em Java
        NumeroComplexo resultado = new NumeroComplexo(1, 0);

        // É multiplicado pelo próprio valor n vezes (Potência)
        for(int i=0; i<n; i++) {
            resultado = resultado.multiplicar(this); // (𝑎𝑐−𝑏𝑑)+(𝑎𝑑+𝑏𝑐)𝑖 como é feita a multiplicação
        }

        return resultado;
    }


    // Simétrico
    public NumeroComplexo simetrico() {

        //Basta colocar o sinal de - para inverter
        double novoReal = -this.real;
        double novoImaginario = -this.imaginario;

        return new NumeroComplexo(novoReal, novoImaginario);
    }


    // Conjugado
    public NumeroComplexo conjugado() {
        return new NumeroComplexo(this.real, -this.imaginario);
    }


    // Inverso
    public NumeroComplexo inverso() {

        // O divisor comum (a^2 + b^2)
        double denominador = Math.pow(this.real, 2) + Math.pow(this.imaginario, 2);

        double novoReal = this.real / denominador; // a / denominador
        double novoImaginario = -this.imaginario / denominador; // -b / denominador

        return new NumeroComplexo(novoReal, novoImaginario);
    }
    // -------------------------------------------------------------------------
}
