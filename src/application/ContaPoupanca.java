package application;

public class ContaPoupanca extends ContaBancaria {
    private double taxaRendimento;

    public ContaPoupanca(String numeroConta, double saldoInicial, double taxaRendimento) {
        super(numeroConta, saldoInicial);
        this.taxaRendimento = taxaRendimento;
    }

    public double getTaxaRendimento() {
        return taxaRendimento;
    }

    public void setTaxaRendimento(double taxaRendimento) {
        this.taxaRendimento = taxaRendimento;
    }

    @Override
    public String getTipoConta() {
        return "ContaPoupanca";
    }

    public double calcularRendimento() {
        return getSaldo() * taxaRendimento;
    }
}