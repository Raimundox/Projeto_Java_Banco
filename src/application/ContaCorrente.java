package application;

public class ContaCorrente extends ContaBancaria {
    private double limiteChequeEspecial;

    public ContaCorrente(String numeroConta, double saldoInicial, double limiteChequeEspecial) {
        super(numeroConta, saldoInicial);
        this.limiteChequeEspecial = limiteChequeEspecial;
    }

    public double getLimiteChequeEspecial() {
        return limiteChequeEspecial;
    }

    public void setLimiteChequeEspecial(double limiteChequeEspecial) {
        this.limiteChequeEspecial = limiteChequeEspecial;
    }

    @Override
    public String getTipoConta() {
        return "ContaCorrente";
    }

    @Override
    public void sacar(double valor) throws SaldoInsuficienteException {
        if (valor > getSaldo() + limiteChequeEspecial) {
            throw new SaldoInsuficienteException("Saldo insuficiente!");
        }

        if (valor <= getSaldo()) {
            setSaldo(valor);
        } else {
            double restante = valor - getSaldo();
            setSaldo(0);
            limiteChequeEspecial -= restante;
        }
    }

}