package Layout;

import application.Banco;
import application.SaldoInsuficienteException;
import application.Usuario;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Saque implements ActionListener {
    private JFrame frame;
    private JButton saqueButton, cancelarButton;
    private JTextField valorTextField;
    private String cpfUsuario; // Adicione isso para identificar a conta
    private String numeroConta;

    public Saque(String cpfUsuario) { // Recebe o número da conta como parâmetro
        this.cpfUsuario = cpfUsuario;

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

        carregarInformacoesUsuario();

        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == saqueButton) {
            try {
                sacar();
            } catch (SQLException | SaldoInsuficienteException ex) {
                JOptionPane.showMessageDialog(frame, "Erro ao realizar saque: " + ex.getMessage());
            }
        } else if (e.getSource() == cancelarButton) {
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

    private void sacar() throws SQLException, SaldoInsuficienteException {
        double valor = Double.parseDouble(valorTextField.getText());


        Banco contaDAO = new Banco();
        contaDAO.sacar(numeroConta, valor);

        JOptionPane.showMessageDialog(frame, "Saque de R$ " + valor + " realizado com sucesso.");

        frame.dispose();
        new Perfil(cpfUsuario);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new Saque("12345"); // Passe um número de conta válido
            }
        });
    }
}