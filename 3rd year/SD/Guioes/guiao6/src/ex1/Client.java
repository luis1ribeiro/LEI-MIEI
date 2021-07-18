package ex1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client {

    public static void main(String[] args) throws IOException{

        Socket socket = new Socket("127.0.0.1",12345);

        // Lê o input do terminal
        BufferedReader reader_terminal = new BufferedReader(new InputStreamReader(System.in));

        // Lê o input do socket
        BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        // Escreve no output do socket
        PrintWriter writer = new PrintWriter(socket.getOutputStream());

        String line;
        while((line = reader_terminal.readLine()) != null){
            writer.println(line);
            writer.flush();
            System.out.println(reader.readLine());
        }
        socket.shutdownOutput();
        socket.shutdownInput();
        socket.close();
    }
}