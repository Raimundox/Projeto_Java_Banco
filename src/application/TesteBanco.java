package application;

public class TesteBanco {
    public static void main(String[] args) {
        ContaCorrente contaCorrente = new ContaCorrente("12345", 1000.0, 500.0);
        ContaPoupanca contaPoupanca = new ContaPoupanca("54321", 2000.0, 0.05);

        try {
            // Realizando algumas movimentações bancárias
            contaCorrente.depositar(500.0);
            contaCorrente.sacar(200.0);

            contaPoupanca.depositar(1000.0);
            contaPoupanca.sacar(300.0);

            // Calculando o rendimento da conta poupança
            double rendimento = contaPoupanca.calcularRendimento();
            System.out.println("Rendimento da conta poupança: " + rendimento);
        } catch (SaldoInsuficienteException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }
}