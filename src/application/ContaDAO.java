package application;

import java.sql.*;

public class ContaDAO {
    private Connection connection;

    public ContaDAO() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/projeto_java_banco";
        String user = "root";
        String password = "password";
        connection = DriverManager.getConnection(url, user, password);
    }

    public void salvar(ContaBancaria conta) throws SQLException {
        String query = "INSERT INTO contas (numero_conta, tipo_conta, saldo, limite_cheque_especial, taxa_rendimento) VALUES (?, ?, ?, ?, ?)";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, conta.getNumeroConta());
        if (conta instanceof ContaCorrente) {
            statement.setString(2, "corrente");
            statement.setDouble(4, ((ContaCorrente) conta).getLimiteChequeEspecial());
        } else {
            statement.setString(2, "poupanca");
            statement.setNull(4, Types.DOUBLE);
        }
        statement.setDouble(3, conta.getSaldo());
        statement.setDouble(5, (conta instanceof ContaPoupanca) ? ((ContaPoupanca) conta).getTaxaRendimento() : 0);
        statement.executeUpdate();
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

    private void atualizarSaldo(ContaBancaria conta) throws SQLException {
        String query = "UPDATE contas SET saldo = ? WHERE numero_conta = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setDouble(1, conta.getSaldo());
        statement.setString(2, conta.getNumeroConta());
        statement.executeUpdate();
    }
}
