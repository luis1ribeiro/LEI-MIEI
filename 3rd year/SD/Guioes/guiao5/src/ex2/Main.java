package ex2;

public class Main {
    public static void main(String [] args){
       try{

           Warehouse w = new Warehouse();
           w.supply("item1", 1);
           w.supply("item2", 2);
           w.supply("item3", 3);
           System.out.println(w);
           Thread t1 = new Thread(() -> {
               try {
                   w.consume(new String[]{"item1"});
               } catch (InterruptedException e) {
                   e.printStackTrace();
               }
           });
           Thread t2 = new Thread(() -> {
               try {
                   w.consume(new String[]{"item2", "item3"});
               } catch (InterruptedException e) {
                   e.printStackTrace();
               }
           });
           t1.start();
           t2.start();
           t1.join();
           t1.join();
           System.out.println(w);
       }
       catch (Exception e) {}
    }
}
