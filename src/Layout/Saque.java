package Layout;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Saque implements ActionListener {
    private JFrame frame;
    private JButton saqueButton, cancelarButton;
    private JTextField valorTextField;

    public Saque() {
        frame = new JFrame("Saque");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 700);
        frame.setLayout(null);

        JLabel titleLabel = new JLabel("Saque");
        titleLabel.setBounds(350, 30, 100, 30);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        frame.add(titleLabel);

        JLabel valorLabel = new JLabel("Valor:");
        valorLabel.setBounds(300, 100, 60, 30);
        frame.add(valorLabel);

        valorTextField = new JTextField();
        valorTextField.setBounds(350, 100, 150, 30);
        frame.add(valorTextField);

        saqueButton = new JButton("Saque");
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
        double valor = Double.parseDouble(valorTextField.getText());


        JOptionPane.showMessageDialog(frame, "Saque de R$ " + valor + " realizado com sucesso.");

    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new Perfil();
            }
        });
    }
}
