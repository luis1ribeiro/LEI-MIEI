import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.InputMismatchException;

public class Worker implements Runnable {

    private Socket s;
    private Ficheiros fich;

    public Worker(Socket s, Ficheiros fich) {
        this.s = s;
        this.fich = fich;
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
                        case "using":
                            this.fich.using(args[1]);
                            break;
                        case "notUsing":
                            this.fich.notUsing(args[1], Boolean.parseBoolean(args[2]));
                            break;
                        case "startBackup":
                            this.fich.startBackup();
                            break;
                        case "endBackup":
                            this.fich.endBackup();
                            break;
                    }
                } catch (InputMismatchException e){
                }
            }
        } catch (IOException | InterruptedException ignored ) {}
    }
}