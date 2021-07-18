package ex1;
import java.io.*;

public class Ex1 implements Runnable {
    private int unt1lj;

    public void run() {
        for(int i=1; i<=this.unt1lj; i++)
        System.out.println(i);
    }

    Ex1(int I){
        this.unt1lj = I;
    }

    public static void main(String args[]) {
        int N = 10;
        int I = 10;
        Thread[] list = new Thread[N];

        for(int i=0; i<N; i++){
            list[i]= new Thread(new Ex1(I));
            list[i].start();
        }
        try{
            for(int i=0; i<N; i++){
                list[i].join();
            }
        } catch (InterruptedException e) {}
    }
}