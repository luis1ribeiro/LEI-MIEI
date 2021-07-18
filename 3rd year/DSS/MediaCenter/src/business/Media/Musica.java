package business.Media;


public class Musica extends Media {
    String artista;

    public Musica(String path, String nome, String genero,String artista) {
        super(path,nome, genero);
        this.artista=artista;
    }

    @Override
    public String getPath() {
        return super.getPath();
    }

    @Override
    public void setPath(String path) {
        super.setPath(path);
    }

    @Override
    public String getNome() {
        return super.getNome();
    }

    @Override
    public void setNome(String nome) {
        super.setNome(nome);
    }

    @Override
    public String getGenero() {
        return super.getGenero();
    }

    @Override
    public void setGenero(String genero) {
        super.setGenero(genero);
    }

    public String getArtista() {
        return this.artista;
    }

    public void setArtista(String artista) {
        this.artista=artista;
    }
}
