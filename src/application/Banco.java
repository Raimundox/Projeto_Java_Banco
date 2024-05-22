package application;

import java.sql.*;

public class Banco {
    private Connection connection;

    public Banco() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/projeto_java_banco";
        String user = "root";
        String password = "password";
        connection = DriverManager.getConnection(url, user, password);
    }

    public ContaBancaria carregar(String numeroConta) throws SQLException {
        String query = "SELECT * FROM contas WHERE numero_conta = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, numeroConta);
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            String tipoConta = resultSet.getString("tipo_conta");
            double saldo = resultSet.getDouble("saldo");
            if (tipoConta.equals("corrente")) {
                double limiteChequeEspecial = resultSet.getDouble("limite_cheque_especial");
                return new ContaCorrente(numeroConta, saldo, limiteChequeEspecial);
            } else {
                double taxaRendimento = resultSet.getDouble("taxa_rendimento");
                return new ContaPoupanca(numeroConta, saldo, taxaRendimento);
            }
        }
        return null;
    }

    public void transferirSaldo(ContaBancaria origem, ContaBancaria destino, double valor) throws SQLException, SaldoInsuficienteException {
        connection.setAutoCommit(false);

        try {
            origem.sacar(valor);
            destino.depositar(valor);
            atualizarSaldo(origem);
            atualizarSaldo(destino);
            connection.commit();
        } catch (SQLException | SaldoInsuficienteException e) {
            connection.rollback();
            throw e;
        } finally {
            connection.setAutoCommit(true);
        }
    }

    public void sacar(String numeroConta, double valor) throws SQLException, SaldoInsuficienteException {
        ContaBancaria conta = carregar(numeroConta);
        if (conta == null) {
            throw new SQLException("Conta não encontrada.");
        }

        conta.sacar(valor);
        atualizarSaldo(conta);
    }

    public void depositar(String numeroConta, double valor) throws SQLException {
        ContaBancaria conta = carregar(numeroConta);
        if (conta == null) {
            throw new SQLException("Conta não encontrada.");
        }

        conta.depositar(valor);
        atualizarSaldo(conta);
    }

    private void atualizarSaldo(ContaBancaria conta) throws SQLException {
        String query = "UPDATE contas SET saldo = ? WHERE numero_conta = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setDouble(1, conta.getSaldo());
        statement.setString(2, conta.getNumeroConta());
        statement.executeUpdate();
    }
}
