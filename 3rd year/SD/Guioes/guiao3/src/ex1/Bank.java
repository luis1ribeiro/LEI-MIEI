package ex1;

import java.util.*;
import java.util.concurrent.locks.ReentrantLock;

public class Bank{
    private Map<Integer,Conta> contas;
    private ReentrantLock lockA;

    Bank(){
        this.contas = new HashMap();
        this.lockA = new ReentrantLock();
    }

    public Conta getConta(int i) {
        return this.contas.get(i);
    }

    public int criar(){
        lockA.lock();
        contas.put(contas.size()+1, new Conta());
        lockA.unlock();
        return contas.size();
    }

    public int eliminar(int id) throws ContaInvalida{
        lockA.lock();
        if(contas.containsKey(id)){
            int saldo = contas.get(id).consulta();
            contas.remove(id);
            lockA.unlock();
            return saldo;
        }
        else {
            lockA.unlock();
            throw new ContaInvalida(id);
        }
    }

    public int total(List<Integer> ids){
//        return ids.stream().reduce(0, (acc, x) -> acc + contas.get(x).consulta());
        lockA.lock();
        int total=0;
        for(int i = 0 ; i< ids.size(); i++){
            total += contas.get(ids.get(i)).consulta();
        }
        lockA.unlock();
        return total;
    }

    public static void main(String [] args) throws InterruptedException {
        Bank banco = new Bank();
        banco.criar();
        banco.criar();
        Cliente c1 = new Cliente(banco,0);
        Cliente c2 = new Cliente(banco,1);
        Thread t1 = new Thread(c1);
        Thread t2 = new Thread(c2);
        t1.start();
        t2.start();
        System.out.println("cenas");
        banco.total(Arrays.asList(1,0));
        t1.join();
        t2.join();
    }

}
