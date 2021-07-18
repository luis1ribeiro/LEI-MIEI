package Exceptions;

public class NaoPodeAlterarCategoriaException extends Exception{
    public NaoPodeAlterarCategoriaException(){
        super();
    }

    public NaoPodeAlterarCategoriaException(String s){
        super(s);
    }
}
