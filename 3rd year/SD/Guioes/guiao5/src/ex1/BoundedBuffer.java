package ex1;

import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.Condition;

public class BoundedBuffer {
    private int [] buff;
    private int size;
    private ReentrantLock lockA;
    private Condition gets;
    private Condition puts;

    BoundedBuffer(int size){
        this.buff = new int[size];
        this.size = 0;
        this.lockA = new ReentrantLock();
        this.gets = this.lockA.newCondition();
        this.puts = this.lockA.newCondition();
    }

    public void put(int v) throws InterruptedException{
        this.lockA.lock();
        while(this.size == this.buff.length){
            this.puts.await();
        }
        this.buff[this.size++] = v;
        this.gets.signalAll();
        this.lockA.unlock();
    }

    public int get() throws InterruptedException{
        this.lockA.lock();
        while(this.size == 0){
            this.gets.await();
        }
        int v = this.buff[-- this.size];;
        this.puts.signalAll();
        this.lockA.unlock();
        return v;
    }
}
