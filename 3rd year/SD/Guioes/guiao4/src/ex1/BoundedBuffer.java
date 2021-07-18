package ex1;

public class BoundedBuffer{
    int [] buff;
    int size;

    BoundedBuffer(int size){
        this.buff = new int[size];
        this.size=0;
    }

    public synchronized void put(int v) throws InterruptedException {
        while(this.size== buff.length){
            this.wait();
        }
        this.buff[this.size++] = v;
        this.notifyAll();
    }

    public synchronized int get() throws InterruptedException{
        while(buff.length==0){
            this.wait();
        }
        int v = this.buff[--this.size];
        this.notifyAll();
        return v;
    }

    public void run(){

    }
}
