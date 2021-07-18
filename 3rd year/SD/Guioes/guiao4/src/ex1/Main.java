package ex1;

public class Main {
    public static void mains(String [] args) throws InterruptedException {
        BoundedBuffer buff = new BoundedBuffer(10);
        Thread t1 = new Thread();
        t1.start();
        t1.join();
    }

}
