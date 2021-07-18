package ex2;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Item {
    private ReentrantLock lockA;
    private Condition isEmpty;
    private int quantity;

    Item(){
        this.quantity = 0;
        this.lockA = new ReentrantLock();
        this.isEmpty = this.lockA.newCondition();
    }

    public void supply(int quant){
        this.lockA.lock();
        this.quantity += quant;
        this.isEmpty.signalAll();
        this.lockA.unlock();
    }


    public void consume() throws InterruptedException {
        while(quantity==0){
            this.isEmpty.await();
        }
        this.lockA.lock();
        this.quantity--;
        this.lockA.unlock();
    }

}
