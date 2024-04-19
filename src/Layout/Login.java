package Layout;

import javax.swing.*;
import java.awt.*;

public class Login extends JFrame {
    public Login() {
        setTitle("Login");
        setSize(800, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 10, 10, 10); // Espaçamento entre os componentes

        JLabel usernameLabel = new JLabel("Username:");
        panel.add(usernameLabel, gbc);

        gbc.gridy++;
        JTextField usernameField = new JTextField(20);
        panel.add(usernameField, gbc);

        gbc.gridy++;
        JLabel passwordLabel = new JLabel("Password:");
        panel.add(passwordLabel, gbc);

        gbc.gridy++;
        JPasswordField passwordField = new JPasswordField(20);
        panel.add(passwordField, gbc);

        gbc.gridy++;
        JButton loginButton = new JButton("Login");
        loginButton.setBounds(460, 200, 170, 50);
        loginButton.setFont(new Font("Arial", Font.BOLD, 20));
        loginButton.addActionListener(e -> {
            // Aqui você pode adicionar a lógica de autenticação
            // Exemplo: validar o username e password
            // Se for válido, pode fechar a janela de login e abrir a próxima janela
            dispose(); // Fecha a janela de login após o login bem-sucedido
            // Insira aqui o código para abrir a próxima janela
            new Perfil();
        });
        panel.add(loginButton, gbc);

        add(panel);

        setLocationRelativeTo(null); // Centraliza a janela na tela
        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Login::new);
    }
}
