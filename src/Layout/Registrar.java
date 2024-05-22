package Layout;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import application.Usuario;

public class Registrar extends JFrame implements ActionListener {
    private JTextField usernameField, cpfField, dataNascimentoField;
    private JPasswordField passwordField;
    private JComboBox<String> tipoContaComboBox;

    public Registrar() {
        setTitle("Registrar");
        setSize(800, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(Color.DARK_GRAY);

        JLabel usernameLabel = new JLabel("Nome:");
        usernameLabel.setBounds(250, 100, 80, 25);
        usernameLabel.setForeground(Color.WHITE);
        panel.add(usernameLabel);

        usernameField = new JTextField(20);
        usernameField.setBounds(300, 100, 200, 25);
        panel.add(usernameField);

        JLabel cpfLabel = new JLabel("CPF:");
        cpfLabel.setBounds(260, 150, 80, 25);
        cpfLabel.setForeground(Color.WHITE);
        panel.add(cpfLabel);

        cpfField = new JTextField(20);
        cpfField.setBounds(300, 150, 200, 25);
        panel.add(cpfField);

        JLabel dataNascimentoLabel = new JLabel("Data de Nascimento:");
        dataNascimentoLabel.setBounds(170, 200, 150, 25);
        dataNascimentoLabel.setForeground(Color.WHITE);
        panel.add(dataNascimentoLabel);

        dataNascimentoField = new JTextField(20);
        dataNascimentoField.setBounds(300, 200, 200, 25);
        panel.add(dataNascimentoField);

        JLabel passwordLabel = new JLabel("Senha:");
        passwordLabel.setBounds(245, 250, 80, 25);
        passwordLabel.setForeground(Color.WHITE);
        panel.add(passwordLabel);

        passwordField = new JPasswordField(20);
        passwordField.setBounds(300, 250, 200, 25);
        panel.add(passwordField);

        JLabel tipoContaLabel = new JLabel("Tipo de Conta:");
        tipoContaLabel.setBounds(200, 300, 100, 25);
        tipoContaLabel.setForeground(Color.WHITE);
        panel.add(tipoContaLabel);

        String[] tiposConta = {"Conta Corrente", "Conta Poupança"};
        tipoContaComboBox = new JComboBox<>(tiposConta);
        tipoContaComboBox.setBounds(300, 300, 200, 25);
        panel.add(tipoContaComboBox);

        JButton backButton = new JButton("Voltar");
        backButton.setBounds(200, 400, 150, 40);
        backButton.setFont(new Font("Arial", Font.BOLD, 20));
        backButton.addActionListener(e -> openLoginWindow());
        panel.add(backButton);

        JButton registerButton = new JButton("Registrar");
        registerButton.setBounds(400, 400, 150, 40);
        registerButton.setFont(new Font("Arial", Font.BOLD, 20));
        registerButton.addActionListener(this);
        panel.add(registerButton);

        getContentPane().add(panel);
        setVisible(true);
    }

    private void openLoginWindow() {
        dispose();
        new Login();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("Registrar")) {
            String nome = usernameField.getText();
            String cpf = cpfField.getText();
            String dataNascimento = dataNascimentoField.getText();
            String senha = new String(passwordField.getPassword());
            String tipoConta = (String) tipoContaComboBox.getSelectedItem();

            if (usuarioJaExiste(cpf)) {
                JOptionPane.showMessageDialog(this, "Erro: Usuário já existe.");
                return;
            }
            if (usuarioJaExiste(nome)) {
                JOptionPane.showMessageDialog(this, "Erro: Usuário já existe.");
                return;
            }

            if (!isValidDate(dataNascimento)) {
                JOptionPane.showMessageDialog(this
                        , "Erro: Data de nascimento inválida.");
                return;
            }
            if (!isValidName(nome)) {
                JOptionPane.showMessageDialog(this, "Erro: Nome inválido.");
                return;
            }
            if (!isValidCPF(cpf)) {
                JOptionPane.showMessageDialog(this, "Erro: CPF inválido.");
                return;
            }

            // Criando uma instância da classe Registro com os dados fornecidos
            Usuario registro = new Usuario(nome, cpf, dataNascimento, senha, tipoConta);
            try {
                registro.registrarUsuario();
                JOptionPane.showMessageDialog(this, "Usuário registrado com sucesso!");
                openLoginWindow();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Erro ao registrar usuário: " + ex.getMessage());
            }
        }
    }
    private boolean usuarioJaExiste(String cpf) {
        Usuario usuario = null;
        try {
            usuario = new Usuario();
            return usuario.verificarExistenciaUsuario(cpf);
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Erro ao verificar a existência do usuário.");
            return false;
        } finally {
            if (usuario != null) {
                try {
                    usuario.fecharConexao();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }
    private boolean isValidName(String name) {
        return name.matches("[a-zA-Z ]+");
    }

    private boolean isValidCPF(String cpf) {
        return cpf.matches("\\d{11}");
    }
    private boolean isValidDate(String date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        sdf.setLenient(false);
        try {
            sdf.parse(date);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Registrar::new);
    }
}