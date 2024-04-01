package application;

public interface MovimentacaoBancaria {
    void depositar(double valor);
    void sacar(double valor) throws SaldoInsuficienteException;
}
