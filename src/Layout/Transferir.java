package Layout;

import javax.swing.*;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;

import application.*;

public class Transferir implements ActionListener {
    private JButton depositarButton, voltarButton;
    private JFrame frame;
    private JTextField saldoTextField, destinoTextField;
    private String cpfUsuario;
    private String numeroConta;

    public Transferir(String cpfUsuario) {
        this.cpfUsuario = cpfUsuario;

        frame = new JFrame("Transferir");

        depositarButton = new JButton("Transferir");
        voltarButton = new JButton("Voltar");

        depositarButton.addActionListener(this);
        voltarButton.addActionListener(this);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 700);
        frame.setLayout(null);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);

        voltarButton.setBounds(250, 350, 150, 40);
        voltarButton.setFont(new Font("Arial", Font.BOLD, 20));
        frame.add(voltarButton);

        depositarButton.setBounds(420, 350, 150, 40);
        depositarButton.setFont(new Font("Arial", Font.BOLD, 20));
        frame.add(depositarButton);

        saldoTextField = new JTextField();
        saldoTextField.setBounds(300, 200, 200, 30);
        frame.add(saldoTextField);

        destinoTextField = new JTextField();
        destinoTextField.setBounds(300, 300, 200, 30);
        frame.add(destinoTextField);

        JLabel saldoLabel = new JLabel("Saldo:");
        saldoLabel.setBounds(212, 200, 80, 30);
        frame.add(saldoLabel);

        JLabel destinoLabel = new JLabel("Destino:");
        destinoLabel.setBounds(200, 300, 80, 30);
        frame.add(destinoLabel);

        carregarInformacoesUsuario();

        frame.setVisible(true);

        ((AbstractDocument) saldoTextField.getDocument()).setDocumentFilter(new DocumentFilter() {
            @Override
            public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
                String newText = fb.getDocument().getText(0, fb.getDocument().getLength()) + text;
                if (newText.matches("\\d*")) {
                    super.replace(fb, offset, length, text, attrs);
                }
            }
        });
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == depositarButton) {
            String saldoStr = saldoTextField.getText();
            String cpfUsuarioDestino = destinoTextField.getText();

            try {
                double saldo = Double.parseDouble(saldoStr);
                Banco contaDAO = new Banco();
                ContaBancaria contaOrigem = contaDAO.carregar(numeroConta);
                ContaBancaria contaDestino = contaDAO.carregar(cpfUsuarioDestino);

                if (contaDestino != null) {
                    if (!contaOrigem.getNumeroConta().equals(contaDestino.getNumeroConta())) {
                        contaDAO.transferirSaldo(contaOrigem, contaDestino, saldo);

                        JOptionPane.showMessageDialog(frame, "Transferência realizada com sucesso!");

                        frame.dispose();

                        new Perfil(cpfUsuario);
                    } else {
                        JOptionPane.showMessageDialog(frame, "Você não pode realizar transferência para a mesma conta.");
                    }
                } else {
                    JOptionPane.showMessageDialog(frame, "Conta de destino não encontrada.");
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frame, "Valor de saldo inválido.");
            } catch (SQLException | SaldoInsuficienteException ex) {
                JOptionPane.showMessageDialog(frame, "Erro ao transferir saldo: " + ex.getMessage());
            }
        } else if (e.getSource() == voltarButton) {
            frame.dispose();

            new Perfil(cpfUsuario);
        }
    }

    private void carregarInformacoesUsuario() {
        Usuario usuario = null;
        try {
            usuario = new Usuario();
            ResultSet resultSet = usuario.obterInformacoesUsuario(cpfUsuario);
            if (resultSet.next()) {
                numeroConta = resultSet.getString("numero_conta");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(frame, "Erro ao carregar informações do usuário.");
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

}
