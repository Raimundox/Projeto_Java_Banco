package application;

import java.sql.SQLException;

public class TesteBanco {
    public static void main(String[] args) {
        try {
            ContaDAO contaDAO = new ContaDAO();

            ContaBancaria contaCorrente = contaDAO.carregar("12345");
            ContaBancaria contaPoupanca = contaDAO.carregar("54321");

            // Realizando movimentações bancárias
            contaDAO.transferirSaldo(contaCorrente, contaPoupanca, 300.0);

            // Exibindo informações das contas
            System.out.println("Saldo da conta corrente: " + contaCorrente.getSaldo());
            System.out.println("Saldo da conta poupança: " + contaPoupanca.getSaldo());
        } catch (SaldoInsuficienteException | SQLException e) {
            System.out.println("Erro ao realizar movimentação bancária: " + e.getMessage());
        }
    }
}

