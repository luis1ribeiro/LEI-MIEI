import Exceptions.*;
import MVC.MediaCenterController;
import business.Media.Media;
import business.Utilizador.Utilizador;

import java.io.*;
import java.net.Socket;
import java.util.List;

public class MultiThreadedServer implements Runnable{

    private Socket s;
    private MediaCenterController controller;

    public MultiThreadedServer(Socket s,MediaCenterController c){
        this.s = s;
        this.controller=c;
    }

    public void loginConvidado (){
        try{
            PrintWriter pw = new PrintWriter(new OutputStreamWriter(this.s.getOutputStream()));
            this.controller.loginConvidado();
            pw.println("Sucesso");
            pw.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void terminarSessao (){
        try{
            PrintWriter pw = new PrintWriter(new OutputStreamWriter(this.s.getOutputStream()));
            this.controller.terminarSessao();
            pw.println("Sucesso");
            pw.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void getListUsers (){
        try{
            ObjectOutputStream out = new ObjectOutputStream(this.s.getOutputStream());
            List<Utilizador> l = this.controller.getListUsers();
            out.writeObject(l);
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void upload (String line){
        String [] args = line.split("\\?");
        try{
            String path=this.controller.upload(args[1],args[2],args[3],args[4]);
            int tam = Integer.parseInt(args[5]);
            OutputStream out = new FileOutputStream(path);
            byte[] bytes = new byte[tam*2];
            InputStream in = this.s.getInputStream();
            int count;
            int tcount=0;
            while (tcount<tam && (count = in.read(bytes)) > 0) {
                tcount+=count;
                out.write(bytes, 0, count);
            }
            out.flush();
            PrintWriter pw = new PrintWriter(new OutputStreamWriter(this.s.getOutputStream()));
            pw.println("Sucesso");
            pw.flush();
        } catch (ConteudoInvalidoException e){
            try{
                PrintWriter pw = new PrintWriter(new OutputStreamWriter(this.s.getOutputStream()));
                pw.println("Conteudo Invalido");
                pw.flush();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        } catch (ConteudoDuplicadoException e) {
            try{
                PrintWriter pw = new PrintWriter(new OutputStreamWriter(this.s.getOutputStream()));
                pw.println("Conteudo Duplicado");
                pw.flush();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void removeUtilizador(String line) {
        String[] args = line.split("\\?");
        try {
            PrintWriter pw = new PrintWriter(new OutputStreamWriter(this.s.getOutputStream()));
            this.controller.removeUtilizador(args[1]);
            pw.println("Sucesso");
            pw.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void iniciaSessao(String line){
        String[] args = line.split("\\?");
        try {
            PrintWriter pw = new PrintWriter(new OutputStreamWriter(this.s.getOutputStream()));
            this.controller.iniciaSessao(args[1],args[2],args[3]);
            pw.println("Sucesso");
            pw.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (EmailNaoExisteException e) {
            try{
                PrintWriter pw = new PrintWriter(new OutputStreamWriter(this.s.getOutputStream()));
                pw.println("Email não existe");
                pw.flush();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        } catch (NaoEAdminException e) {
            try{
                PrintWriter pw = new PrintWriter(new OutputStreamWriter(this.s.getOutputStream()));
                pw.println("Não é administrador");
                pw.flush();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        } catch (NaoEUserException e) {
            try{
                PrintWriter pw = new PrintWriter(new OutputStreamWriter(this.s.getOutputStream()));
                pw.println("A sua conta é de administrador");
                pw.flush();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        } catch (PasswordErradaException e) {
            try{
                PrintWriter pw = new PrintWriter(new OutputStreamWriter(this.s.getOutputStream()));
                pw.println("Password errada");
                pw.flush();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    public void alteraCategoria (String line){
        String[] args = line.split("\\?");
        try {
            PrintWriter pw = new PrintWriter(new OutputStreamWriter(this.s.getOutputStream()));
            this.controller.alteraCategoria(args[1],args[2]);
            pw.println("Sucesso");
            pw.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }catch (NaoPodeAlterarCategoriaException e){
            try{
                PrintWriter pw = new PrintWriter(new OutputStreamWriter(this.s.getOutputStream()));
                pw.println("CONVIDADO: Não pode alterar categoria");
                pw.flush();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    public void isConvidado (String line){
        try {
            PrintWriter pw = new PrintWriter(new OutputStreamWriter(this.s.getOutputStream()));
            String bool = String.valueOf(this.controller.isConvidado());
            pw.println(bool);
            pw.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void getListVideos (String line){
        try{
            ObjectOutputStream out = new ObjectOutputStream(this.s.getOutputStream());
            List<Media> l = this.controller.getListVideos();
            out.writeObject(l);
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void getListMusicas (String line){
        try{
            ObjectOutputStream out = new ObjectOutputStream(this.s.getOutputStream());
            List<Media> l = this.controller.getListMusicas();
            out.writeObject(l);
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void registarUtilizador (String line){
        String[] args = line.split("\\?");
        try {
            PrintWriter pw = new PrintWriter(new OutputStreamWriter(this.s.getOutputStream()));
            this.controller.registarUtilizador(args[1],args[2],args[3]);
            pw.println("Sucesso");
            pw.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }catch (EmailJaExisteException e){
            try{
                PrintWriter pw = new PrintWriter(new OutputStreamWriter(this.s.getOutputStream()));
                pw.println("Email já existe");
                pw.flush();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    public void reproduzirConteudo (String line){
        String[] args = line.split("\\?");
        try{
            PrintWriter pw = new PrintWriter(new OutputStreamWriter(this.s.getOutputStream()));
            File m = new File (System.getProperty("user.dir")+File.separator+"musicas"+File.separator+args[1]);
            int tam = (int) m.length();
            pw.println(tam);
            pw.flush();
            byte [] bytes  = new byte [tam];
            InputStream input = new FileInputStream (m);
            OutputStream output = this.s.getOutputStream();
            int count;
            while ((count = input.read(bytes)) > 0) {
                output.write(bytes, 0, count);
            }
            output.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void download (String line){
        String [] args = line.split("\\?");
        try{
            PrintWriter pw = new PrintWriter(new OutputStreamWriter(this.s.getOutputStream()));
            File m = new File (this.controller.download(args[1],args[2]));
            int tam = (int) m.length();
            pw.println(tam);
            pw.flush();
            byte [] bytes  = new byte [tam];
            InputStream input = new FileInputStream (m);
            OutputStream output = this.s.getOutputStream();
            int count;
            while ((count = input.read(bytes)) > 0) {
                output.write(bytes, 0, count);
            }
            output.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }catch (NaoPodeFazerDownloadException e){
            try{
                PrintWriter pw = new PrintWriter(new OutputStreamWriter(this.s.getOutputStream()));
                pw.println("-1");
                pw.flush();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    public void removeConteudo(String line){
        String [] args = line.split("\\?");
        try {
            PrintWriter pw = new PrintWriter(new OutputStreamWriter(this.s.getOutputStream()));
            this.controller.removeConteudo(args[1]);
            pw.println("Sucesso");
            pw.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }catch (NaoPodeRemoverException e){
            try{
                PrintWriter pw = new PrintWriter(new OutputStreamWriter(this.s.getOutputStream()));
                pw.println("Não pode remover o conteúdo");
                pw.flush();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    public void filter (String line){
        String [] args = line.split("\\?");
        try{
            ObjectOutputStream out = new ObjectOutputStream(this.s.getOutputStream());
            List<Media> l = this.controller.filter(args[1],args[2],args[3]);
            out.writeObject(l);
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void getPlaylists (String line){
        try{
            ObjectOutputStream out = new ObjectOutputStream(this.s.getOutputStream());
            List<String> l = this.controller.getPlaylists();
            out.writeObject(l);
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void criarPlaylist (String line){
        String [] args = line.split("\\?");
        try {
            PrintWriter pw = new PrintWriter(new OutputStreamWriter(this.s.getOutputStream()));
            this.controller.criarPlaylist(args[1],args[2],args[3],args[4]);
            pw.println("Sucesso");
            pw.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }catch (PlaylistVaziaException e){
            try{
                PrintWriter pw = new PrintWriter(new OutputStreamWriter(this.s.getOutputStream()));
                pw.println("Playlist vazia não criada!");
                pw.flush();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    public void musicasPlaylist (String line){
        String [] args = line.split("\\?");
        try{
            ObjectOutputStream out = new ObjectOutputStream(this.s.getOutputStream());
            List<Media> l = this.controller.musicasPlaylist(args[1]);
            out.writeObject(l);
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void editarConta (String line){
        String[] args = line.split("\\?");
        try {
            PrintWriter pw = new PrintWriter(new OutputStreamWriter(this.s.getOutputStream()));
            this.controller.editaConta(args[1],args[2],args[3]);
            pw.println("Sucesso");
            pw.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }catch (EmailJaExisteException e){
            try{
                PrintWriter pw = new PrintWriter(new OutputStreamWriter(this.s.getOutputStream()));
                pw.println("Email já existe");
                pw.flush();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    public void interpretaLinha(String line){
        String command = line.split("\\?")[0];
        switch (command){
            case "loginConvidado":
                loginConvidado();
                break;
            case "terminarSessao":
                terminarSessao();
                break;
            case "getListaUsers":
                getListUsers();
                break;
            case "upload":
                upload(line);
                break;
            case "removeUtilizador":
                removeUtilizador(line);
                break;
            case "iniciaSessao":
                iniciaSessao(line);
                break;
            case "alteraCategoria":
                alteraCategoria(line);
                break;
            case "isConvidado":
                isConvidado(line);
                break;
            case "getListVideos":
                getListVideos(line);
                break;
            case "getListMusicas":
                getListMusicas(line);
                break;
            case "registarUtilizador":
                registarUtilizador(line);
                break;
            case "reproduzirConteudo":
                reproduzirConteudo(line);
                break;
            case "download":
                download(line);
                break;
            case "removeConteudo":
                removeConteudo(line);
                break;
            case "filter":
                filter(line);
                break;
            case "getPlaylists":
                getPlaylists(line);
                break;
            case "criarPlaylist":
                criarPlaylist(line);
                break;
            case "musicasPlaylist":
                musicasPlaylist(line);
                break;
            case "editarConta":
                editarConta(line);
                break;
        }
    }



    public void run() {
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(s.getInputStream()));
            String line;
            File f= new File(System.getProperty("user.dir")+File.separator+"logs"+File.separator+"serverlogs.bak");
            PrintWriter pw = new PrintWriter(f);
            while (true) {
                line = br.readLine();
                pw.println(line);
                pw.flush();
                if(line == null)
                    break;
                interpretaLinha(line);
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        try {
            s.shutdownOutput();
            s.shutdownInput();
            s.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}