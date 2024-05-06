package Layout;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Perfil implements ActionListener {
    private JButton jButton, jButton2, jButton3;
    private JFrame frame;
    private JLabel nameLabel, saldoLabel, iD;

    public Perfil() {
        frame = new JFrame("Banco");

        iD = new JLabel("ID:AB-1750");
        iD.setBounds(150, 150, 200, 10);
        iD.setFont(new Font("Arial", Font.BOLD, 10));
        frame.add(iD);

        nameLabel = new JLabel("Pereira Santos Cardoso");
        nameLabel.setBounds(150, 120, 250, 30);
        nameLabel.setFont(new Font("Arial", Font.BOLD, 20));
        frame.add(nameLabel);

        saldoLabel = new JLabel("Saldo: R$ 500.25"); // Inicializa o saldo com 0.00
        saldoLabel.setBounds(150, 160, 200, 30);
        saldoLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        frame.add(saldoLabel);

        jButton = new JButton("Saque");
        jButton.setBounds(145, 230, 170, 50);
        jButton.setFont(new Font("Arial", Font.BOLD, 20));
        jButton.addActionListener(this);
        frame.add(jButton);

        jButton2 = new JButton("Transferir");
        jButton2.setBounds(330, 230, 170, 50);
        jButton2.setFont(new Font("Arial", Font.BOLD, 20));
        jButton2.addActionListener(this);
        frame.add(jButton2);

        jButton3 = new JButton("Depositar");
        jButton3.setBounds(515, 230, 170, 50);
        jButton3.setFont(new Font("Arial", Font.BOLD, 20));
        jButton3.addActionListener(this);

        frame.add(jButton2);
        frame.add(jButton3);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 700);
        frame.setLayout(null);
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == jButton) {
            System.out.println("Botão Saque clicado!");
            frame.dispose();
            new Saque();

        } else if (e.getSource() == jButton2) {
            System.out.println("Botão Depósito clicado!");
            frame.dispose();
            new Transferir();
        }else if (e.getSource() == jButton3){
            frame.dispose();
            new Deposito();
        }

        }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new Perfil();
            }
        });
    }
}
