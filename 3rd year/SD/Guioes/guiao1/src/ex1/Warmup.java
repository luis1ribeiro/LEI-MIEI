package ex1;

import java.io.*;
import java.lang.Object;

public class Warmup {
    public static void main (String [] args) {
        try{
            BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
            String ola = null;


            while(true){
                ola  = in.readLine();
                if(ola==null){
                    break;
                }
                System.out.println(ola);
            }
        }
        catch(IOException e){}
    }
}