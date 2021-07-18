package MVC;

import business.Utilizador.*;
import business.Playlist.*;
import business.Media.*;
import data.*;

import java.io.File;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class SistemaGestao {
    private UtilizadoresDAO utilizadores;
    private PlaylistsDAO playlists;
    private MediaDAO biblioteca;

    public SistemaGestao(){
        this.utilizadores = new UtilizadoresDAO();
        this.biblioteca = new MediaDAO();
        this.playlists = new PlaylistsDAO();
        Admin a = new Admin ("super@dragoes.fcp","porto","Super Dragoes");
        this.utilizadores.put("super@dragoes.fcp",a);
    }


    public boolean emailExiste(String email) {
        return this.utilizadores.containsKey(email);
    }

    public void criaConta(String email, String password, String nome){
        Utilizador u = new User(email,password,nome);
        this.utilizadores.put(u.getEmail(),u);
    }


    public boolean conteudoValido(String path) {
        String ext = path.substring(path.lastIndexOf("."));
        return ext.equals(".mp3") || ext.equals(".mp4");
    }


    public boolean conteudoDuplicado(String path) {
        return this.biblioteca.containsKey(path);
    }

    public void upDuplicado(String path,String user){
        this.biblioteca.putRelationUp(path,user);
    }

    public void guardaConteudo(String nome, String artista, String genero,String user) {
        String ext,path;
        Media m;
        if (artista==null){
            ext=".mp4";
            path = nome + ext;
            m = new Video(path,nome,genero);
        }
        else{
            ext=".mp3";
            path = nome + " - " + artista + ext;
            m = new Musica (path,nome,genero,artista);
        }
        this.biblioteca.put(path,m);
        this.biblioteca.putRelationUp(path,user);
    }

    public boolean verificaCredenciais(String email, String password) {
        Utilizador u = this.utilizadores.get(email);
        return u.getEmail().equals(email) && u.getPassword().equals(password);
    }

    public boolean dadosSaoValidos(String email, String password, String nome) {
        boolean existe = emailExiste(email);
        return !existe;
    }

    public void editaConta(String email, String password, String nome,String user) {
        this.utilizadores.put(user,new User (email, password, nome));
    }

    public void editaCategoria(String path, String novogen,String user) {
        Media m = this.biblioteca.get(path);
        m.setGenero(novogen);
        this.biblioteca.putRelationGenero(path,m,user);
    }

    public boolean podeRemover (String fich,String user){
        return (this.biblioteca.temMusica(fich,user));
    }

    public void removeConteudo (String fich,String user){
        this.biblioteca.removeInd(fich,user);
        if (this.biblioteca.noMoreRelations(fich)){
            this.biblioteca.remove(fich);
            removeFicheiro(fich);
        }
    }

    // exception
    public void removeFicheiro(String fich){
        String path = System.getProperty("user.dir")+File.separator+"musicas"+File.separator+fich;
        File file = new File(path);
        file.delete();
    }

    public List<Media> filterArtista(String artista,String user){
        return this.biblioteca.getMediaUser(user).stream().filter(m-> m instanceof Musica && ((Musica)m).getArtista().equals(artista)).collect(Collectors.toList());
    }

    public List<Media> filterGenero(String genero, String user){
        return this.biblioteca.getMediaUser(user).stream().filter(m-> m.getGenero().equals(genero)).collect(Collectors.toList());
    }

    public List<Media> playlistGenero(String genero, String nome, String user){
        Playlist p = new Playlist(nome,user);
        List<Media> l = this.biblioteca.getMediaUser(user).stream().
                filter(m->m instanceof Musica && m.getGenero().equals(genero)).map(m->(Musica)m).collect(Collectors.toList());
        if (l.isEmpty()) return l;
        this.playlists.put(nome,p);
        l.forEach(m-> this.playlists.putRelation(m,p));
        return l;
    }

    public List<Media> playlistArtista(String artista, String nome,String user){
        Playlist p = new Playlist(nome,user);
        List<Media> l = this.biblioteca.getMediaUser(user).stream().
                filter(m->m instanceof Musica && m.getGenero().equals(artista)).map(m->(Musica)m).collect(Collectors.toList());
        if (l.isEmpty()) return l;
        this.playlists.put(nome,p);
        l.forEach(m-> this.playlists.putRelation(m,p));
        return l;
    }

    public List<Media> playlistAleatoria(String nome, int tam,String user){
        Playlist p = new Playlist(nome,user);
        List<Media> l = this.biblioteca.getMediaUser(user).stream().
                filter(m->m instanceof Musica).map(m->(Musica)m).collect(Collectors.toList());
        if (l.isEmpty()) return l;
        this.playlists.put(nome,p);
        Collections.shuffle(l);
        l.stream().limit(tam).forEach(m-> this.playlists.putRelation(m,p));
        return l;
    }

    public List<Media> playlistVideoGenero(String genero, String nome,String user){
        Playlist p = new Playlist(nome,user);
        List<Media> l = this.biblioteca.getMediaUser(user).stream().
                filter(m->m instanceof Video && m.getGenero().equals(genero)).map(m->(Video)m).collect(Collectors.toList());
        if (l.isEmpty()) return l;
        this.playlists.put(nome,p);
        l.forEach(m-> this.playlists.putRelation(m,p));
        return l;
    }

    public List<Media> playlistVideoAleatoria(String nome, int tam,String user){
        Playlist p = new Playlist(nome,user);
        List<Media> l = this.biblioteca.getMediaUser(user).stream().
                filter(m->m instanceof Video).map(m->(Video)m).collect(Collectors.toList());
        if (l.isEmpty()) return l;
        this.playlists.put(nome,p);
        Collections.shuffle(l);
        l.stream().limit(tam).forEach(m-> this.playlists.putRelation(m,p));
        return l;
    }

    public List<Media> getListMusicas(String user){
        return this.biblioteca.getMediaUser(user).stream().filter(m-> m instanceof Musica).map(m->(Musica)m).collect(Collectors.toList());
    }

    public List<Media> getListVideos(String user){
        return this.biblioteca.getMediaUser(user).stream().filter(m-> m instanceof Video).map(m->(Video)m).collect(Collectors.toList());
    }

    public List<Utilizador> getListUsers(){
        return this.utilizadores.values().stream().filter(u-> u instanceof User).map(u-> (User)u).collect(Collectors.toList());
    }

    public boolean isAdmin(String email){
        return this.utilizadores.get(email) instanceof Admin;
    }

    public void removeUtilizador(String email){
        this.utilizadores.remove(email);
    }

    public List<String> getPlaylists(String user){
        return this.playlists.values().stream().filter(m -> m.getUser().equals(user)).map(Playlist::getNome).collect(Collectors.toList());
    }

    public List<Media> musicasPlaylist (String nome,String user){
        List<String> media = this.playlists.getMediaPlaylist(nome,user);
        List<Media> usermedia = this.biblioteca.getMediaUser(user);
        return usermedia.stream().filter(m -> media.contains(m.getPath())).collect(Collectors.toList());
    }
}