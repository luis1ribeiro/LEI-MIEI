package ex2;

public class Consumer {
    private Warehouse wh;

    private void run() throws InterruptedException {
        this.wh.consume(new String[]{"item1", "item2", "item3"});
    }
}
