package MVC;

import Exceptions.*;
import business.Media.Media;
import business.Media.Musica;
import business.Media.Video;
import business.Utilizador.Utilizador;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


public class MediaCenterController {
    private String userLogado;
    private SistemaGestao model;

    public MediaCenterController(){
        this.model = new SistemaGestao();
        createMusicPath();
    }

    public MediaCenterController(SistemaGestao model){
        this.model = model;
        createMusicPath();
    }

    public String getUserLogado() {
        return userLogado;
    }

    public void setUserLogado(String userLogado) {
        this.userLogado = userLogado;
    }

    public SistemaGestao getModel() {
        return model;
    }

    public void setModel(SistemaGestao model) {
        this.model = model;
    }


    public void iniciaSessao (String email, String password,String tipo) throws EmailNaoExisteException, PasswordErradaException, NaoEAdminException, NaoEUserException {
        if (tipo.equals("Admin") && !this.model.isAdmin(email)) throw new NaoEAdminException();
        if (tipo.equals("User") && this.model.isAdmin(email)) throw new NaoEUserException();
        if (!this.model.emailExiste(email)){
            throw new EmailNaoExisteException ();
        }
        if (!this.model.verificaCredenciais(email,password)){
            throw new PasswordErradaException ();
        }
        this.setUserLogado(email);
    }

    public void registarUtilizador(String email, String password, String nome) throws EmailJaExisteException{
        if (this.model.emailExiste(email)){
            throw new EmailJaExisteException ();
        }
        this.model.criaConta(email,password,nome);
    }

    public void terminarSessao(){this.setUserLogado("NULL");}


    public String upload(String path, String nome, String genero, String artista) throws ConteudoDuplicadoException, ConteudoInvalidoException{
        if (!this.model.conteudoValido(path)){
            throw new ConteudoInvalidoException ();
        }
        String ext = path.substring(path.lastIndexOf("."));
        String fich;
        if (ext.equals(".mp3")) fich=nome + " - " + artista + ext;
        else fich=nome + ext;
        if (this.model.conteudoDuplicado(fich)){
            this.model.upDuplicado(fich,this.userLogado);
            throw new ConteudoDuplicadoException ();
        }
        if (ext.equals(".mp4")) this.model.guardaConteudo(nome,null,genero,this.userLogado);
        else this.model.guardaConteudo(nome,artista,genero,this.userLogado);
        return System.getProperty("user.dir")+File.separator+"musicas"+File.separator+fich;
    }

    public void alteraCategoria(String path, String novogen) throws NaoPodeAlterarCategoriaException {
        if (isConvidado()) throw new NaoPodeAlterarCategoriaException();
        this.model.editaCategoria(path,novogen,this.userLogado);
    }

    public void createMusicPath(){
        String musicas_path = System.getProperty("user.dir")+File.separator+"musicas";
        Path path = Paths.get(musicas_path);
        if (!Files.exists(path)) {
            try {
                Files.createDirectories(path);
            } catch (IOException e) {e.printStackTrace();}
        }
    }

    public void removeConteudo(String fich) throws NaoPodeRemoverException{
        if (this.model.podeRemover(fich,this.userLogado))
            this.model.removeConteudo(fich,this.userLogado);
        else throw new NaoPodeRemoverException ();
    }

    public boolean isConvidado(){
        return this.userLogado.equals("CONVIDADO");
    }

    public List<Media> getListMusicas(){
        return this.model.getListMusicas(this.userLogado);
    }

    public List<Media> getListVideos(){
        return this.model.getListVideos(this.userLogado);
    }

    public List<Utilizador> getListUsers(){ return this.model.getListUsers();}

    public void criarPlaylist(String tipo, String campo, String arg, String nome) throws PlaylistVaziaException{
        List<Media> l = new ArrayList<>();
        if (tipo.equals("Video")){
            if (campo.equals("Genero")){
                l=this.model.playlistVideoGenero(arg,nome,this.userLogado);
            }
            if (campo.equals("Aleatoria")){
                l=this.model.playlistVideoAleatoria(nome,Integer.parseInt(arg),this.userLogado);
            }
        }
        else{
            if (campo.equals("Artista")){
                l=this.model.playlistArtista(arg,nome,this.userLogado);
            }
            if (campo.equals("Genero")){
                l=this.model.playlistGenero(arg,nome,this.userLogado);
            }
            if (campo.equals("Aleatoria")){
                l=this.model.playlistAleatoria(nome,Integer.parseInt(arg),this.userLogado);
            }
        }
        if (l.isEmpty()) throw new PlaylistVaziaException ();
    }

    public void loginConvidado(){
        this.setUserLogado("CONVIDADO");
    }

    public void removeUtilizador(String email){
        this.model.removeUtilizador(email);
    }

    public String download (String fich, String path) throws NaoPodeFazerDownloadException {
        if (!this.model.podeRemover(fich,this.userLogado)){
            throw new NaoPodeFazerDownloadException ();
        }
        String origem = System.getProperty("user.dir")+File.separator+"musicas"+File.separator+fich;
        return origem;
    }

    public List<Media> filter(String tipo, String campo, String arg){
        if (campo.equals("Genero")){
            if (tipo.equals("Video")){
                return this.model.filterGenero(arg,this.userLogado).stream().filter(m->m instanceof Video).map(m->(Video) m).collect(Collectors.toList());
            }
            else{
                return this.model.filterGenero(arg,this.userLogado).stream().filter(m->m instanceof Musica).map(m->(Musica) m).collect(Collectors.toList());
            }
        }
        if (campo.equals("Artista")){
            return this.model.filterArtista(arg,this.userLogado).stream().filter(m->m instanceof Musica).map(m->(Musica) m).collect(Collectors.toList());
        }
        return new ArrayList<>();
    }

    public List<String> getPlaylists(){
        return this.model.getPlaylists(this.userLogado);
    }

    public List<Media> musicasPlaylist (String nome){
        return this.model.musicasPlaylist(nome,this.userLogado);
    }

    public void editaConta (String email, String pwd,String nome)throws EmailJaExisteException{
        if (email.equals(this.userLogado)){
            this.model.editaConta(email,pwd,nome,this.userLogado);
            return;
        }
        if (this.model.dadosSaoValidos(email,pwd,nome)){
            this.model.editaConta(email,pwd,nome,this.userLogado);
        }
        else throw new EmailJaExisteException();
    }
}