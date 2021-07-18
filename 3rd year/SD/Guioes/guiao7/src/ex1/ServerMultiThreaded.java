package ex1;

import java.io.*;
import java.net.Socket;
import java.util.List;

public class ServerMultiThreaded implements Runnable {

    private Socket s;
    private Banco b;

    public ServerMultiThreaded(Socket s, Banco b) {
        this.s = s;
        this.b = b;
    }

    public void run() {
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(s.getInputStream()));
            PrintWriter pw = new PrintWriter(new OutputStreamWriter(s.getOutputStream()));
            while (true) {
                String line;
                String answer = "Comando inválido";
                line = br.readLine();
                if(line == null)
                    break;

                // Tokenize da linha recebida
                String[] parts = line.split(" ");

                try {
                    if (parts[0].equals("transferir")) {
                        b.transferir(Integer.parseInt(parts[1]), Integer.parseInt(parts[2]),Double.parseDouble(parts[3]));
                        answer = "Foi transferido " + parts[1] + " da conta " + parts[2] + " para a conta " + parts[3];
                    } else if (parts[0].equals("createAccount")) {
                        int res = b.criarConta(Double.parseDouble(parts[1]));
                        answer = Integer.toString(res);

                    } else if (parts[0].equals("closeAccount")) {
                        double res = b.fecharConta(Integer.parseInt(parts[1]));
                        answer = Double.toString(res);

                    } else if (parts[0].equals("totalBalance")) {
                        String[] accounts_str = parts[1].split(",");

                        int[] accounts_id = new int[accounts_str.length];
                        for (int i = 0; i < accounts_id.length; i++)
                            accounts_id[i] = Integer.parseInt(accounts_str[i]);

                        double res = b.consultarTotal(accounts_id);
                        answer = Double.toString(res);

                    } else if (parts[0].equals("consultar"))
                        double res = b.consultar(Integer.parseInt(parts[1]));
                        answer = Double.toString(res);

                    } else if (parts[0].equals("credito")) {
                        b.levantar(Integer.parseInt(parts[1]), Double.parseDouble(parts[2]));

                    } else if (parts[0].equals("debito")) {
                        b.depositar(Integer.parseInt(parts[1]), Double.parseDouble(parts[2]));

                    }
                }
                catch(Exception e){
                    answer = e.getMessage();
                }
                pw.println(answer);
                pw.flush();
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}