package business.Playlist;

public class Playlist {
    String nome;
    String user;

    public Playlist(String nom,String user) {
        this.nome = nom;
        this.user=user;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

}
