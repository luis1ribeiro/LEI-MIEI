package ex2;

import static java.lang.Thread.sleep;

public class Producer {
    private Warehouse wh;

    private void run() throws InterruptedException {
        this.wh.supply("item1",1);
        sleep(3000);
        this.wh.supply("item2",1);
        sleep(3000);
        this.wh.supply("item3",1);
    }
}
