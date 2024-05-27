package Layout;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;

import application.ContaPoupanca;
import application.Usuario;

public class Perfil implements ActionListener {
    private JButton jButton, jButton2, jButton3;
    private JFrame frame;
    private JLabel nameLabel, numeroConta, cpfLabel, saldoLabel, tipoConta, limiteChequeEspecialLabel, taxaRendimentoLabel, rendimentoCalculadoLabel;
    private String cpfUsuario;

    public Perfil(String cpfUsuario) {
        frame = new JFrame("Perfil do Usuário");

        nameLabel = new JLabel("Nome: ");
        nameLabel.setBounds(50, 50, 250, 30);
        nameLabel.setFont(new Font("Arial", Font.BOLD, 20));
        frame.add(nameLabel);
        this.cpfUsuario = cpfUsuario;

        tipoConta = new JLabel("Tipo de Conta: ");
        tipoConta.setBounds(50, 100, 250, 30);
        tipoConta.setFont(new Font("Arial", Font.BOLD, 20));
        frame.add(tipoConta);

        numeroConta = new JLabel("N° da Conta: ");
        numeroConta.setBounds(50, 150, 250, 30);
        numeroConta.setFont(new Font("Arial", Font.BOLD, 20));
        frame.add(numeroConta);

        cpfLabel = new JLabel("CPF: ");
        cpfLabel.setBounds(50, 200, 250, 30);
        cpfLabel.setFont(new Font("Arial", Font.BOLD, 20));
        frame.add(cpfLabel);

        saldoLabel = new JLabel("Saldo: ");
        saldoLabel.setBounds(50, 250, 250, 30);
        saldoLabel.setFont(new Font("Arial", Font.BOLD, 20));
        frame.add(saldoLabel);

        limiteChequeEspecialLabel = new JLabel("Limite Cheque Especial: ");
        limiteChequeEspecialLabel.setBounds(50, 300, 400, 30);
        limiteChequeEspecialLabel.setFont(new Font("Arial", Font.BOLD, 20));
        frame.add(limiteChequeEspecialLabel);

        taxaRendimentoLabel = new JLabel("Taxa de Rendimento: ");
        taxaRendimentoLabel.setBounds(50, 300, 400, 30);
        taxaRendimentoLabel.setFont(new Font("Arial", Font.BOLD, 20));
        frame.add(taxaRendimentoLabel);

        rendimentoCalculadoLabel = new JLabel("Rendimento Calculado: ");
        rendimentoCalculadoLabel.setBounds(50, 350, 400, 30);
        rendimentoCalculadoLabel.setFont(new Font("Arial", Font.BOLD, 20));
        frame.add(rendimentoCalculadoLabel);

        jButton = new JButton("Saque");
        jButton.setBounds(50, 400, 170, 50);
        jButton.setFont(new Font("Arial", Font.BOLD, 20));
        jButton.addActionListener(this);
        frame.add(jButton);

        jButton2 = new JButton("Transferir");
        jButton2.setBounds(250, 400, 170, 50);
        jButton2.setFont(new Font("Arial", Font.BOLD, 20));
        jButton2.addActionListener(this);
        frame.add(jButton2);

        jButton3 = new JButton("Depositar");
        jButton3.setBounds(450, 400, 170, 50);
        jButton3.setFont(new Font("Arial", Font.BOLD, 20));
        jButton3.addActionListener(this);
        frame.add(jButton3);

        carregarInformacoesUsuario();

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
            new Saque(cpfUsuario);

        } else if (e.getSource() == jButton2) {
            System.out.println("Botão Depósito clicado!");
            frame.dispose();
            new Transferir(cpfUsuario);
        }else if (e.getSource() == jButton3){
            frame.dispose();
            new Deposito(cpfUsuario);
        }

    }

    private void carregarInformacoesUsuario() {
        Usuario usuario = null;

        try {
            usuario = new Usuario();
            ResultSet resultSet = usuario.obterInformacoesUsuario(cpfUsuario);
            if (resultSet.next()) {
                tipoConta.setText(resultSet.getString("tipo_conta"));
                nameLabel.setText("Olá " + resultSet.getString("nome")+ "!");
                numeroConta.setText("N° da conta:: " + resultSet.getString("numero_conta"));
                cpfLabel.setText("CPF: " + resultSet.getString("cpf"));
                saldoLabel.setText("Saldo: R$ " + resultSet.getDouble("saldo"));


                String tipoDeConta = resultSet.getString("tipo_conta");
                if (tipoDeConta.equalsIgnoreCase("Conta Corrente")) {
                    limiteChequeEspecialLabel.setText("Limite Cheque Especial: R$ " + resultSet.getDouble("limite_cheque_especial"));
                    rendimentoCalculadoLabel.setText("");
                } else if (tipoDeConta.equalsIgnoreCase("Conta Poupança")) {
                    double saldo = resultSet.getDouble("saldo");
                    double taxaRendimento = resultSet.getDouble("taxa_rendimento");
                    String numero_conta = resultSet.getString("numero_conta");

                    ContaPoupanca contapoupanca = new ContaPoupanca(numero_conta, saldo, taxaRendimento);
                    double rendimentoCalculado = contapoupanca.calcularRendimento();

                    taxaRendimentoLabel.setText("Taxa de Rendimento: " + 0.5 + "%");
                    rendimentoCalculadoLabel.setText("Rendimento Calculado: R$ " + rendimentoCalculado);
                    limiteChequeEspecialLabel.setText("");
                }
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
