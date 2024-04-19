package Layout;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Deposito implements ActionListener {
    private JFrame frame;
    private JButton saqueButton, cancelarButton;
    private JTextField valorTextField;

    public Deposito() {
        frame = new JFrame("Deposito");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 700);
        frame.setLayout(null);

        JLabel titleLabel = new JLabel("Deposito");
        titleLabel.setBounds(350, 30, 100, 30);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        frame.add(titleLabel);

        JLabel valorLabel = new JLabel("Valor:");
        valorLabel.setBounds(300, 100, 60, 30);
        frame.add(valorLabel);

        valorTextField = new JTextField();
        valorTextField.setBounds(350, 100, 170, 30);
        frame.add(valorTextField);

        saqueButton = new JButton("Deposito");
        saqueButton.setBounds(250, 200, 170, 50);
        saqueButton.setFont(new Font("Arial", Font.BOLD, 20));
        saqueButton.addActionListener(this);
        frame.add(saqueButton);

        cancelarButton = new JButton("Cancelar");
        cancelarButton.setBounds(460, 200, 170, 50);
        cancelarButton.setFont(new Font("Arial", Font.BOLD, 20));
        cancelarButton.addActionListener(this);
        frame.add(cancelarButton);

        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == saqueButton) {
            sacar();
        } else if (e.getSource() == cancelarButton) {
            frame.dispose(); // Fecha a janela de saque
            new Perfil();
        }
    }

    private void sacar() {
        // Obtenha o valor digitado pelo usuário
        double valor = Double.parseDouble(valorTextField.getText());

        // Lógica de saque - aqui você pode implementar sua própria lógica de saque
        // Verifique se o valor é válido e se há saldo suficiente para o saque, etc.
        // Por enquanto, apenas exibiremos uma mensagem informando que o saque foi realizado com sucesso
        JOptionPane.showMessageDialog(frame, "Saque de R$ " + valor + " realizado com sucesso.");

        // Se houver uma lógica mais complexa de saque, você pode implementá-la aqui
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new Perfil();
            }
        });
    }
}
