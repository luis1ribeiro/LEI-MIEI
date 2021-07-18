package Exceptions;

public class PlaylistVaziaException extends Exception {
    public PlaylistVaziaException(){
        super();
    }

    public PlaylistVaziaException(String s){
        super(s);
    }
}
