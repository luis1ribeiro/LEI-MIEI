package ex3;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client {

    public static void main(String[] args) throws Exception {
        Socket s = new Socket("localhost",12345);

        BufferedReader reader_terminal = new BufferedReader(new InputStreamReader(System.in));
        BufferedReader br = new BufferedReader(new InputStreamReader(s.getInputStream()));
        PrintWriter pw = new PrintWriter(s.getOutputStream());

        String line;
        while(true){
            line = reader_terminal.readLine();
            if(line == null)
                break;
            pw.println(line);
            pw.flush();
            System.out.println(br.readLine());
        }
        s.shutdownOutput();
        s.shutdownInput();
        s.close();
    }
}