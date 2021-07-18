package ex1;

public class SemSaldo extends Exception {

    public SemSaldo(double saldo){
        super("Sem Saldo:" + saldo);
    }
}