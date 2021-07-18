package ex1;

import java.util.HashMap;
import java.util.concurrent.locks.ReentrantLock;

public class Banco {
    private HashMap<Integer,Conta> contas;
    private ReentrantLock lockBanco;
    private int nrcontas;

    Banco(int numContas){
        this.contas = new HashMap<>(numContas);
        this.nrcontas = 0;
    }

    public int criarConta(double saldo){
        this.lockBanco.lock();
        int id = this.nrcontas++;
        Conta c = new Conta(id,saldo);
        contas.put(id,c);
        this.lockBanco.unlock();
        return id;
    }

    public double fecharConta(int id) throws ContaInvalida{
        this.lockBanco.lock();
        if(!this.contas.containsKey(id)){
            this.lockBanco.unlock();
            throw new ContaInvalida("Conta Inválida");
        }
        else {
            double money = this.contas.get(id).consulta();
            this.contas.remove(id);
            this.nrcontas--;
            this.lockBanco.unlock();
            return money;
        }
    }



    public double consultar(int id) throws ContaInvalida{
        this.lockBanco.lock();
        if(!this.contas.containsKey(id)){
            this.lockBanco.unlock();
            throw new ContaInvalida("Conta Inválida");
        }
        else {
            this.lockBanco.unlock();
            return this.contas.get(id).consulta();
        }
    }


    public double consultarTotal(int[] contasInput){
        double total=0;
        this.lockBanco.lock();
        for(int i=0; i< contasInput.length; i++) {
            total += this.contas.get(contasInput[i]).consulta();
        }
        this.lockBanco.unlock();
        return total;
    }

    public void levantar(int id, double valor) throws SaldoInsuficiente, ContaInvalida{
        this.lockBanco.lock();
        if(!this.contas.containsKey(id)){
            this.lockBanco.unlock();
            throw new ContaInvalida("Conta Inválida");
        }
        else if (this.contas.get(id).consulta() - valor < 0){
            this.lockBanco.unlock();
            throw new SaldoInsuficiente("Comes no cú que sai mais barato e não suja prato");
        }
        else this.contas.get(id).levantar(valor);
        this.lockBanco.unlock();
    }


    public void depositar(int id, double valor) throws ContaInvalida{
        this.lockBanco.lock();
        if(!this.contas.containsKey(id)){
            this.lockBanco.unlock();
            throw new ContaInvalida("Conta Inválida");
        }
        else{
            this.contas.get(id).depositar(valor);
            this.lockBanco.unlock();
        }

    }
    public void transferir(int conta_origem, int conta_destino, double valor) throws ContaInvalida, SaldoInsuficiente{
        this.lockBanco.lock();
        if(!this.contas.containsKey(conta_destino)){
            this.lockBanco.unlock();
            throw new ContaInvalida("Conta Inválida");
        }
        else if(!this.contas.containsKey(conta_origem)){
            this.lockBanco.unlock();
            throw new ContaInvalida("Conta Inválida");
        }
        else if(this.contas.get(conta_origem).consulta() - valor < 0){
            this.lockBanco.unlock();
            throw new SaldoInsuficiente("RIP, no $$$$");
        }
        else{
            this.contas.get(conta_origem).levantar(valor);
            this.contas.get(conta_destino).depositar(valor);
            this.lockBanco.unlock();
        }

    }

}

