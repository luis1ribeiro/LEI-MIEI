package Exceptions;

public class ConteudoDuplicadoException extends Exception {
    public ConteudoDuplicadoException(){
        super();
    }

    public ConteudoDuplicadoException(String s){
        super(s);
    }
}