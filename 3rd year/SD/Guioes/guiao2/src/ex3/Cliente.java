package ex3;

import java.util.List;

public class Cliente implements Runnable {
    private List<Conta> banco;
    private int id;

    Cliente(List<Conta> conta, int id) {
        this.banco = conta;
        this.id = id;
    }

    public void run() {
        if (id == 1) {
                banco.get(0).transfere(0,1,1000);
        } else {
                banco.get(1).levanta(1000);

        }
    }
}
