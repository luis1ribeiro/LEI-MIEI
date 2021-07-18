package ex1;

public class ContaInvalida extends Exception {

    public ContaInvalida(int id){
        super("Invalid Account: "+id);
    }
}