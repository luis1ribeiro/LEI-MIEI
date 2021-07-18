package ex2;

public class Conta {
    private int saldo;

    public Conta(){
        this.saldo=0;
    }

    public synchronized void deposita(int valor){
        this.saldo+= valor;
    }

    public synchronized void levanta(int valor){
        this.saldo-= valor;
    }

    public synchronized int consulta(){
        return this.saldo;
    }
}
