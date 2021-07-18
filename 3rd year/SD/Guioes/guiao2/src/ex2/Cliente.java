package ex2;

import java.util.ArrayList;
import java.util.List;

public class Cliente implements Runnable{
    private List<Conta> banco;
    private int id;

    Cliente(List<Conta> conta, int id){
        this.banco=conta;
        this.id=id;
    }

    public void run() {
        if(id==1){
            for(int i=0; i<100000; i++){
                banco.get(0).deposita(5);
            }
        }
        else{
            for(int i=0; i<100000; i++){
                banco.get(0).levanta(5);
            }
        }
    }
}
