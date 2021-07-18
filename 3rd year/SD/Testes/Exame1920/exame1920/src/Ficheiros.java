import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Ficheiros {
    private ReentrantLock lock;
    private Condition utiliza;
    private List<String> fich;
    private Queue<Condition> cond;
    private List<String> mod;


    Ficheiros(){
        this.lock = new ReentrantLock();
        this.utiliza = this.lock.newCondition();
        this.fich = new ArrayList<>();
        this.cond = new PriorityQueue<>();
        this.mod = new ArrayList<>();
    }

    public void using (String path) throws InterruptedException {
        this.lock.lock();
        this.cond.add(lock.newCondition());
        while (this.fich.contains(path)){
            this.utiliza.await();
        }
        this.fich.add(path);
        this.lock.unlock();
    }

    public void notUsing (String path, boolean modified){
        this.lock.lock();
        this.fich.remove(path);
        this.cond.remove();
        this.cond.peek().signal();
        if(modified) this.mod.add(path);
        this.lock.unlock();
    }

    public List<String> startBackup() throws InterruptedException {
        this.lock.lock();
        List<String> ret = new ArrayList<>();
        for(int i=0; i< mod.size(); i++){
            String v = this.mod.get(i);
            if(this.fich.contains(v)){
                using(v);
                ret.add(v);
                notUsing(v,false);
            }
            else ret.add(v);
        }
        endBackup();
        this.lock.unlock();
        return ret;
    }

    public void endBackup(){
        this.lock.lock();
        this.mod = new ArrayList<>();
        this.lock.unlock();
    }

}
