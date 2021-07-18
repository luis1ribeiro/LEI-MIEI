import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class ControlPonte {
    private ReentrantLock lock;
    private Condition carro;
    private Queue<Condition> barcos;
    private int nrcarros;
    private boolean levantada;
    private boolean bloqueiacarros;

    ControlPonte(){
        this.lock = new ReentrantLock();
        this.carro = this.lock.newCondition();
        this.barcos = new PriorityQueue<>();
        this.nrcarros = 0;
        this.levantada = false;
        this.bloqueiacarros = false;

    }


    void entra_carro() throws InterruptedException {
        this.lock.lock();
        if (bloqueiacarros || levantada){
            this.carro.await();
        }
        else {
            this.nrcarros++;
        }
        this.lock.unlock();
    }

    void sai_carro() throws InterruptedException {
        this.lock.lock();
        nrcarros--;
        if(nrcarros==0 && this.barcos.size()>0){
            this.barcos.poll().signal();
        }
        this.lock.unlock();

    }

    void entra_barco() throws InterruptedException {
        this.lock.lock();
        if (!levantada && this.barcos.size()==0) {
            TimeUnit.MINUTES.sleep(5);
            this.bloqueiacarros = true;
            this.barcos.add(this.lock.newCondition());
            //this.barcos.last() por o ultimo adicionado em espera
            this.levantada = true;
        }
        else{
            this.barcos.add(this.lock.newCondition());
            this.barcos.wait();
        }
    }


    void sai_barco(){
        this.lock.lock();
        if(this.barcos.size()>0){
            this.barcos.poll().signal();
        }
        else{
            levantada = false;
            bloqueiacarros = false;
            this.carro.signal();
        }
        this.lock.unlock();

    }
}
