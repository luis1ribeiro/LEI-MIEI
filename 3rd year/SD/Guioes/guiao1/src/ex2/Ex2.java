package ex2;


public class Ex2 implements Runnable{
    private int until;
    private Counter c;


    public void run() {
        for(int i=1; i<=until; i++){
            System.out.println(c.getCounter());
            c.increment();
        }
    }

    Ex2 (int I, Counter c) {
        this.until = I;
        this.c = c;
    }

    public static void main(String [] args){
        int N = 10;
        int I = 10;
        Counter c = new Counter();
        Thread[] list = new Thread[N];

        for(int i=0; i<N; i++){
            list[i]= new Thread(new Ex2(I,c));
            list[i].start();
        }
        try{
            for(int i=0; i<N; i++){
                list[i].join();
            }
        } catch (InterruptedException e) {}
    }
}
