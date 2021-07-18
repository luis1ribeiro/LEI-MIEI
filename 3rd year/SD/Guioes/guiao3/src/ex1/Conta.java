package ex1;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Conta {
    private int id;
    private int saldo;

    public Conta(){
        this.id=id;
        this.saldo=0;
    }

    public synchronized void deposita(int valor){
        this.saldo+= valor;
    }

    public synchronized void levanta(int valor){
        this.saldo -= valor;
    }

    public synchronized int consulta(){
        return this.saldo;
    }



}