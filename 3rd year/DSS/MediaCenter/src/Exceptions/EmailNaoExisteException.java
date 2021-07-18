package Exceptions;

public class EmailNaoExisteException extends Exception {
    public EmailNaoExisteException(){
        super();
    }

    public EmailNaoExisteException(String s){
        super(s);
    }
}