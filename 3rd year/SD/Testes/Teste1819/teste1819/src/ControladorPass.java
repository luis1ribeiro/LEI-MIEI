import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class ControladorPass {
    private ReentrantLock lock;
    private List<Queue<Condition>> fila;
    private int terminal;
    private int passageiros;

    ControladorPass(){
        this.lock = new ReentrantLock();
        this.fila = new ArrayList<>();
        this.terminal = 1;
        this.passageiros = 0;
    }


    void requisita_viagem(int origem, int destino) {
        this.lock.lock();
        try{
            this.fila.get(origem).add(lock.newCondition());
            while(terminal!= origem || passageiros == 30){
                this.fila.get(origem).peek().await();
            }
            this.fila.get(origem).peek().signalAll();
            passageiros++;
        }
        catch (Exception e) {};

        this.lock.unlock();
    }

    void espera(int destino){
        this.lock.lock();
        try{
            while(this.terminal != destino){
                while(passageiros < 10) this.wait();
                this.sleep(5000000);
                if(this.terminal < 5) {
                    terminal++;
                }
                else {terminal=1;}
            }
            passageiros--;
            this.fila.get(destino).peek().signalAll();
        }
        catch (Exception e) {};

        this.lock.unlock();
    }
}
