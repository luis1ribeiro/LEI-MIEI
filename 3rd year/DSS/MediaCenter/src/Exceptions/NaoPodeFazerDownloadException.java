package Exceptions;

public class NaoPodeFazerDownloadException extends Exception {
    public NaoPodeFazerDownloadException(){
        super();
    }

    public NaoPodeFazerDownloadException(String s){
        super(s);
    }
}
