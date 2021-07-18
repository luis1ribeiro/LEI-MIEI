package Exceptions;

public class EmailJaExisteException extends Exception{
    public EmailJaExisteException(){
        super();
    }

    public EmailJaExisteException(String s){
        super(s);
    }
}