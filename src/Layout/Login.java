package Layout;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Login extends JFrame implements ActionListener {
    private final JTextField usernameField;
    private final JPasswordField passwordField;

    public Login() {
        setTitle("Login");
        setSize(800, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(Color.GRAY);

        JLabel usernameLabel = new JLabel("Nome:");
        usernameLabel.setBounds(250, 200, 80, 25);
        usernameLabel.setForeground(Color.WHITE);
        panel.add(usernameLabel);

        usernameField = new JTextField(20);
        usernameField.setBounds(300, 200, 200, 25);
        panel.add(usernameField);

        JLabel passwordLabel = new JLabel("CPF:");
        passwordLabel.setBounds(250, 250, 80, 25);
        passwordLabel.setForeground(Color.WHITE);
        panel.add(passwordLabel);

        passwordField = new JPasswordField(20);
        passwordField.setBounds(300, 250, 200, 25);
        panel.add(passwordField);

        JButton registerButton = new JButton("Registrar");
        registerButton.setBounds(200, 300, 170, 50);
        registerButton.setFont(new Font("Arial", Font.BOLD, 20));
        registerButton.addActionListener(e -> openRegistrarWindow());
        panel.add(registerButton);

        JButton loginButton = new JButton("Logar");
        loginButton.setBounds(400, 300, 170, 50);
        loginButton.setFont(new Font("Arial", Font.BOLD, 20));
        loginButton.addActionListener(this);
        panel.add(loginButton);

        getContentPane().add(panel);
        setVisible(true);
    }

    private void openRegistrarWindow() {
        dispose();
        new Registrar();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("Logar")) {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());


            System.out.println("Username: " + username);
            System.out.println("Password: " + password);


            dispose();
            new Perfil();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Login::new);
    }
}
