import MVC.MediaCenterController;
import MVC.SistemaGestao;

import java.net.ServerSocket;
import java.net.Socket;

public class Servidor {
    public static void main(String [] args) throws Exception{
        SistemaGestao model = new SistemaGestao();

        ServerSocket ss = new ServerSocket(12345);

        while(true){
            Socket s = ss.accept();
            new Thread(new MultiThreadedServer(s,new MediaCenterController(model))).start();
        }
    }
}