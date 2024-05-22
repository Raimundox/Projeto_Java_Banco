package Layout;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import application.Usuario;

public class Login implements ActionListener {
    private JFrame frame;
    private JTextField cpfField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private Usuario usuario;

    public Login() {
        try {
            usuario = new Usuario();
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(frame, "Erro ao conectar ao banco de dados.");
            System.exit(1);
        }

        frame = new JFrame("Login");

        JLabel cpfLabel = new JLabel("CPF:");
        cpfLabel.setBounds(50, 50, 150, 30);
        frame.add(cpfLabel);

        cpfField = new JTextField();
        cpfField.setBounds(150, 50, 200, 30);
        frame.add(cpfField);

        JLabel passwordLabel = new JLabel("Senha:");
        passwordLabel.setBounds(50, 100, 150, 30);
        frame.add(passwordLabel);

        passwordField = new JPasswordField();
        passwordField.setBounds(150, 100, 200, 30);
        frame.add(passwordField);

        loginButton = new JButton("Login");
        loginButton.setBounds(150, 150, 100, 30);
        loginButton.addActionListener(this);
        frame.add(loginButton);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);
        frame.setLayout(null);
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String cpf = cpfField.getText();
        String senha = new String(passwordField.getPassword());

        try {
            if (usuario.verificarCredenciais(cpf, senha)) {
                JOptionPane.showMessageDialog(frame, "Login bem-sucedido.");
                frame.dispose();
                new Perfil(cpf);
            } else {
                JOptionPane.showMessageDialog(frame, "CPF ou senha incorretos.");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(frame, "Erro ao verificar credenciais.");
        }
    }

    public static void main(String[] args) {
        new Login();
    }
}