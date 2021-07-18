package ex1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server{

    public static void main(String[] args) throws IOException {

        ServerSocket serverSocket = new ServerSocket(12345);

        while(true){
            Socket socket = serverSocket.accept();

            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter writer = new PrintWriter(socket.getOutputStream());

            String line;
            while((line = reader.readLine()) != null){ // Lê-se uma das linhas que o cliente enviou
                // Vamos escrevê-la de novo ao cliente
                writer.println(line);
                writer.flush();
            }
            socket.shutdownOutput();
            socket.shutdownInput();
            socket.close();
        }
    }
}
