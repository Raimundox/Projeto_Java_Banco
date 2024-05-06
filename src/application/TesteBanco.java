package application;

import java.sql.SQLException;

public class TesteBanco {
    public static void main(String[] args) {
        try {
            ContaDAO contaDAO = new ContaDAO();

            ContaBancaria contaCorrente = new ContaCorrente("123456", 1000.0, 500.0);
            // Salvando a conta corrente no banco de dados
            contaDAO.salvar(contaCorrente);

            // Criando uma conta poupança
            ContaBancaria contaPoupanca = new ContaPoupanca("789012", 500.0, 0.03);
            // Salvando a conta poupança no banco de dados
            contaDAO.salvar(contaPoupanca);

            System.out.println("Saldo da conta poupança: " + contaPoupanca.getSaldo());
        } catch ( SQLException e) {
            System.out.println("Erro ao realizar movimentação bancária: " + e.getMessage());
        }
    }
}

