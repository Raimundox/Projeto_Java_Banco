package Layout;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Registrar extends JFrame implements ActionListener {
    private final JTextField usernameregField;
    private final JPasswordField cpfField;

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
        usernameLabel.setBounds(250, 200, 80, 25);
        usernameLabel.setForeground(Color.WHITE);
        panel.add(usernameLabel);

        usernameregField = new JTextField(20);
        usernameregField.setBounds(300, 200, 200, 25);
        panel.add(usernameregField);

        JLabel cpfLabel = new JLabel("CPF:");
        cpfLabel.setBounds(250, 250, 80, 25);
        cpfLabel.setForeground(Color.WHITE);
        panel.add(cpfLabel);

        cpfField = new JPasswordField(20);
        cpfField.setBounds(300, 250, 200, 25);
        panel.add(cpfField);

        JButton backButton = new JButton("Voltar");
        backButton.setBounds(200, 300, 170, 50);
        backButton.setFont(new Font("Arial", Font.BOLD, 20));
        backButton.addActionListener(e -> openLoginWindow());
        panel.add(backButton);

        JButton registerButton = new JButton("Registrar");
        registerButton.setBounds(400, 300, 170, 50);
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
            String username = usernameregField.getText();
            String password = new String(cpfField.getPassword());

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
