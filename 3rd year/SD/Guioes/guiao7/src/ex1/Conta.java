package ex1;

public class Conta {
    private int id;
    private double saldo;

    Conta(int id, double saldo){
        this.id=id;
        this.saldo=saldo;
    }

    public int getId(){return this.id;}

    public void levantar(double valor){
       this.saldo -= valor;
    }

    public void depositar(double valor){
        this.saldo += valor;
    }

    public double consulta(){
        return this.saldo;
    }
}
