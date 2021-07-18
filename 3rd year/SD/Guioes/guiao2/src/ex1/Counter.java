package ex1;

public class Counter {

    public int counter;

    public Counter(){
        this.counter=0;
    }

    public synchronized void increment(){
        this.counter++;
    }

    public synchronized int getCounter(){
        return this.counter;
    }
}
