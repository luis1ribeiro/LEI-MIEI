package ex1;

import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    public static void main(String [] args) throws Exception {
        ServerSocket ss = new ServerSocket(12345);
        Banco b = new Banco(10);

        while (true){
            Socket s = ss.accept();
            new Thread(new ServerMultiThreaded(s,b)).start();
        }
    }
}