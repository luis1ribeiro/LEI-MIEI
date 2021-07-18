package ex3;

import java.util.ArrayList;
import java.util.List;

public class Bank{

    public static void main(String [] args){
        List<Conta> contas = new ArrayList<>();
        Conta c = new Conta();
        contas.add(c);

        Thread cliente1 = new Thread(new Cliente(contas,1));
        Thread cliente2 = new Thread(new Cliente(contas,2));
        cliente1.start();
        cliente2.start();
        try{
            cliente1.join();
            cliente2.join();
        }
        catch (InterruptedException e) {}

        System.out.println(contas.get(0).consulta());
    }


}