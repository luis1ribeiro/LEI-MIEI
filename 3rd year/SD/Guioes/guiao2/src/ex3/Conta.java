package ex3;

import java.util.ArrayList;
import java.util.List;

public class Conta {
    private int saldo;
    private List<Conta> conta;
    private int id;

    public Conta(){
        this.saldo=0;
        this.conta= new ArrayList<>();
        this.id=id;
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

    public synchronized void transfere(int x, int y, int money){
        conta.get(x).levanta(money);
        conta.get(y).deposita(money);
    }
}