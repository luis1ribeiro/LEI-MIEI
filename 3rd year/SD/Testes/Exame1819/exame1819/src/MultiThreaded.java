import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.InputMismatchException;

public class MultiThreaded implements Runnable {

    private Socket s;
    private ControlPonte cp;

    public MultiThreaded (Socket s, ControlPonte cp) {
        this.s = s;
        this.cp = cp;
    }

    @Override
    public void run(){
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(this.s.getInputStream()));
            PrintWriter out = new PrintWriter(this.s.getOutputStream());

            String value;
            while ((value = in.readLine()) != null){
                String[] args = value.split(" ");
                try {
                    switch (args[0]){
                        case "barco":
                            this.cp.entra_barco();
                            Thread.sleep(Integer.parseInt(args [1]));
                            this.cp.sai_barco();
                            break;
                        case "carro":
                            this.cp.entra_carro();
                            Thread.sleep(Integer.parseInt(args [1]));
                            this.cp.sai_carro();
                            break;
                    }
                } catch (InputMismatchException e){
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException ignored ) {}
    }
}