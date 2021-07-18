package ex2;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Warehouse {
    private ReentrantLock lockA;
    private Condition condition;
    private HashMap<String,Item> stock;



    public void supply (String item, int quantity){
        //this.lockA.lock();
        stock.get(item).supply(quantity);
        //this.lockA.unlock();
    }


    public void consume(String[] items) throws InterruptedException {
        for(int i=0; i< items.length;i++){
            //this.lockA.lock();
            stock.get(items[i]).consume();
        }
        //this.lockA.unlock();

    }

}
