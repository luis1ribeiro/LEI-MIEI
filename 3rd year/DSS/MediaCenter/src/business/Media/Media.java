package business.Media;

import java.io.Serializable;

public abstract class Media implements Serializable {
    String path;
    String nome;
    String genero;

    public Media(String path, String nome, String genero) {
        this.path=path;
        this.nome = nome;
        this.genero = genero;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }
}
