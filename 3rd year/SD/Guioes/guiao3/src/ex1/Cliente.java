package ex1;

import java.util.ArrayList;
import java.util.List;

public class Cliente implements Runnable {
    private Bank banco;
    private int id;

    Cliente(Bank banco, int id) {
        this.banco = banco;
        this.id = id;
    }

    public void run() {
        if(id==1){
            for(int i=0; i<100000; i++){
                banco.getConta(0).deposita(5);
            }
        }
        else{
            for(int i=0; i<100000; i++){
                banco.getConta(1).deposita(5);
            }
        }
    }
}
