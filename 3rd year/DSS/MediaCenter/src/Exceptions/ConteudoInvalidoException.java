package Exceptions;

public class ConteudoInvalidoException extends Exception {
    public ConteudoInvalidoException (){
        super();
    }

    public ConteudoInvalidoException(String s){
        super(s);
    }
}