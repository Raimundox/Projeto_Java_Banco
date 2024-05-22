package application;

import java.sql.*;

public class Usuario {
    private String Nome;
    private String CPF;
    private String DataNascimento;
    private String Senha;
    private String TipoConta;

    private Connection connection;

    public Usuario() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/projeto_java_banco";
        String user = "root";
        String password = "password";
        connection = DriverManager.getConnection(url, user, password);
    }

    public Usuario(String nome, String cpf, String DataNascimento, String senha, String tipoConta) {
        this.Nome = nome;
        this.CPF = cpf;
        this.DataNascimento = DataNascimento; 
        this.Senha = senha;
        this.TipoConta = tipoConta;
    }

    public void registrarUsuario() {
        double limiteChequeEspecial = 0.0;
        double taxaRendimento = 0.0;

        if (TipoConta.equalsIgnoreCase("Conta Corrente")) {
            limiteChequeEspecial = 1000.00;
            taxaRendimento = 0.0;
        } else if (TipoConta.equalsIgnoreCase("Conta Poupança")) {
            limiteChequeEspecial = 0.0;
            taxaRendimento = 0.005 ;
        }

        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/projeto_java_banco", "root", "password")) {
            String sqlUsuarios = "INSERT INTO usuarios (nome, cpf, DataNascimento, senha) VALUES (?, ?, ?, ?)";

            try (PreparedStatement preparedStatement = connection.prepareStatement(sqlUsuarios, Statement.RETURN_GENERATED_KEYS)) {
                preparedStatement.setString(1, Nome);
                preparedStatement.setString(2, CPF);
                String formattedDataNascimento = formatDataNascimento(DataNascimento);
                preparedStatement.setString(3, formattedDataNascimento);
                preparedStatement.setString(4, Senha);

                int linhasAfetadas = preparedStatement.executeUpdate();
                if (linhasAfetadas > 0) {
                    ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
                    if (generatedKeys.next()) {
                        int idUsuario = generatedKeys.getInt(1);
                        String sqlContas = "INSERT INTO contas (id_usuario, tipo_conta, limite_cheque_especial, taxa_rendimento) VALUES (?, ?, ?, ?)";
                        try (PreparedStatement preparedStatementContas = connection.prepareStatement(sqlContas)) {
                            preparedStatementContas.setInt(1, idUsuario);
                            preparedStatementContas.setString(2, TipoConta);
                            preparedStatementContas.setDouble(3, limiteChequeEspecial);
                            preparedStatementContas.setDouble(4, taxaRendimento);
                            int linhasAfetadasContas = preparedStatementContas.executeUpdate();
                            if (linhasAfetadasContas > 0) {
                                System.out.println("Usuário registrado com sucesso!");
                            } else {
                                System.out.println("Erro ao registrar o usuário.");
                            }
                        }
                    }
                } else {
                    System.out.println("Erro ao registrar o usuário.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean verificarCredenciais(String cpf, String senha) throws SQLException {
        String query = "SELECT * FROM usuarios WHERE cpf = ? AND senha = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, cpf);
        statement.setString(2, senha);
        ResultSet resultSet = statement.executeQuery();
        return resultSet.next();
    }

    public ResultSet obterInformacoesUsuario(String cpf) throws SQLException {
        String query = "SELECT u.nome, u.cpf, c.saldo, c.numero_conta, c.tipo_conta, c.limite_cheque_especial, c.taxa_rendimento FROM usuarios u JOIN contas c ON u.id = c.id_usuario WHERE u.cpf = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, cpf);
        return statement.executeQuery();
    }

    public void fecharConexao() throws SQLException {
        if (connection != null && !connection.isClosed()) {
            connection.close();
        }
    }

    public boolean verificarExistenciaUsuario(String cpf) throws SQLException {
        String query = "SELECT 1 FROM usuarios WHERE cpf = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, cpf);
        ResultSet resultSet = statement.executeQuery();
        return resultSet.next();
    }

    private String formatDataNascimento(String DataNascimento) {
        return DataNascimento;
    }
}
