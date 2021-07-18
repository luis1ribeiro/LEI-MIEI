import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Worker implements Runnable{
    private Socket s;
    private ControladorPass cp;

    public Worker(Socket s, ControladorPass cp){
        this.s = s;
        this.cp = cp;
    }

    public void run(){
        try{
            BufferedReader in = new BufferedReader(new InputStreamReader(this.s.getInputStream()));
            PrintWriter out = new PrintWriter(this.s.getOutputStream());

            String value;
            while ((value = in.readLine()) != null){
                String [] args = value.split(" ");
                try {
                    switch (args[0]){
                        case"requisita_viagem":
                            this.cp.requisita_viagem(Integer.parseInt(args[1]),Integer.parseInt(args[2]));
                        case"espera":
                            this.cp.espera(Integer.parseInt(args[1]));
                            break;
                    }
                }
                catch (Exception e) {}
            }


        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
