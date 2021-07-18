package data;

import ConnectInfo.ConnectInfo;
import business.Media.*;

import java.sql.*;
import java.util.*;

public class MediaDAO implements Map<String,Media>{
    private static MediaDAO inst = null;
    private ConnectInfo con = new ConnectInfo();
    private String url  = con.getUrl();
    private String user = con.getUser();
    private String pwd  = con.getPwd();

    public MediaDAO() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            createDatabase();
            createTables();
        }
        catch (ClassNotFoundException e) {
            throw new NullPointerException(e.getMessage());
        }
    }

    public static MediaDAO getInstance() {
        if (inst == null) {
            inst = new MediaDAO();
        }
        return inst;
    }

    public void createDatabase(){
        try (Connection conn = DriverManager.getConnection(this.con.getNewSchemaURL(),this.user,this.pwd)){
            Statement stm = conn.createStatement();
            stm.executeUpdate("CREATE SCHEMA IF NOT EXISTS mediacenter");
        }
        catch (Exception e) {throw new NullPointerException(e.getMessage());}
    }

    public void createTables(){
        try (Connection conn = DriverManager.getConnection(this.url,this.user,this.pwd)){
            Statement stm = conn.createStatement();
            String sql = "CREATE TABLE IF NOT EXISTS mediacenter.TMedia (" +
                    "path VARCHAR(255) NOT NULL," +
                    "nome VARCHAR(255) NOT NULL," +
                    "genero VARCHAR(255) NOT NULL, " +
                    "artista VARCHAR(255) NOT NULL," +
                    "tipo VARCHAR(255) NOT NULL," +
                    "PRIMARY KEY (path)," +
                    "UNIQUE INDEX path_UNIQUE (path ASC) VISIBLE)";
            stm.executeUpdate(sql);
            sql = "CREATE TABLE IF NOT EXISTS mediacenter.MediaUserGenero (" +
                    "TMedia_path VARCHAR(255) NOT NULL," +
                    "genero VARCHAR(255) NOT NULL," +
                    "TUtilizadores_email VARCHAR(255) NOT NULL," +
                    "PRIMARY KEY (TMedia_path, TUtilizadores_email)," +
                    "INDEX fk_TMedia_has_TUtilizadores_TUtilizadores1_idx (TUtilizadores_email ASC) VISIBLE," +
                    "INDEX fk_TMedia_has_TUtilizadores_TMedia_idx (TMedia_path ASC) VISIBLE," +
                    "CONSTRAINT fk_TMedia_has_TUtilizadores_TMedia " +
                    "FOREIGN KEY (TMedia_path) " +
                    "REFERENCES mediacenter.TMedia (path) " +
                    "ON DELETE CASCADE " +
                    "ON UPDATE CASCADE, " +
                    "CONSTRAINT fk_TMedia_has_TUtilizadores_TUtilizadores1 " +
                    "FOREIGN KEY (TUtilizadores_email) " +
                    "REFERENCES mediacenter.TUtilizadores(email) " +
                    "ON DELETE CASCADE " +
                    "ON UPDATE CASCADE)";
            stm.executeUpdate(sql);
            sql = "CREATE TABLE IF NOT EXISTS mediacenter.MediaUser (" +
                    "TMedia_path VARCHAR(255) NOT NULL," +
                    "TUtilizadores_email VARCHAR(255) NOT NULL," +
                    "PRIMARY KEY (TMedia_path, TUtilizadores_email)," +
                    "INDEX fk_TMedia_has_TUtilizadores1_TUtilizadores1_idx (TUtilizadores_email ASC) VISIBLE," +
                    "INDEX fk_TMedia_has_TUtilizadores1_TMedia1_idx (TMedia_path ASC) VISIBLE," +
                    "CONSTRAINT fk_TMedia_has_TUtilizadores1_TMedia1 " +
                    "FOREIGN KEY (TMedia_path) " +
                    "REFERENCES mediacenter.TMedia (path) " +
                    "ON DELETE CASCADE " +
                    "ON UPDATE CASCADE, " +
                    "CONSTRAINT fk_TMedia_has_TUtilizadores1_TUtilizadores1 " +
                    "FOREIGN KEY (TUtilizadores_email) " +
                    "REFERENCES mediacenter.TUtilizadores(email) " +
                    "ON DELETE CASCADE " +
                    "ON UPDATE CASCADE)";
            stm.executeUpdate(sql);
        }
        catch (Exception e) {throw new NullPointerException(e.getMessage());}
    }

    public void clear () {
        try (Connection conn = DriverManager.getConnection(this.url,this.user,this.pwd)) {
            Statement stm = conn.createStatement();
            stm.executeUpdate("DELETE FROM TMedia");
        }
        catch (Exception e) {throw new NullPointerException(e.getMessage());}
    }

    public boolean containsKey(Object key) throws NullPointerException {
        try (Connection conn = DriverManager.getConnection(this.url,this.user,this.pwd)) {
            Statement stm = conn.createStatement();
            String sql = "SELECT nome FROM TMedia WHERE path='"+(String)key+"'";
            ResultSet rs = stm.executeQuery(sql);
            return rs.next();
        }
        catch (Exception e) {throw new NullPointerException(e.getMessage());}
    }

    public boolean temMusica (String key, String user){
        try (Connection conn = DriverManager.getConnection(this.url,this.user,this.pwd)) {
            PreparedStatement stm = conn.prepareStatement( "SELECT TMedia_path FROM MediaUser WHERE TMedia_path=? AND TUtilizadores_email=?");
            stm.setString(1,key);
            stm.setString(2,user);
            ResultSet rs = stm.executeQuery();
            return rs.next();
        }
        catch (Exception e) {throw new NullPointerException(e.getMessage());}
    }

    public boolean noMoreRelations (String key){
        try (Connection conn = DriverManager.getConnection(this.url,this.user,this.pwd)) {
            PreparedStatement stm = conn.prepareStatement( "SELECT TMedia_path FROM MediaUser WHERE TMedia_path=?");
            stm.setString(1,key);
            ResultSet rs = stm.executeQuery();
            return !rs.next();
        }
        catch (Exception e) {throw new NullPointerException(e.getMessage());}
    }

    public Media get(Object key) {
        try (Connection conn = DriverManager.getConnection(this.url,this.user,this.pwd)) {
            Statement stm = conn.createStatement();
            String sql = "SELECT * FROM TMedia WHERE path='"+(String)key+"'";
            ResultSet rs = stm.executeQuery(sql);
            if (rs.next()) {
                String path=rs.getString("path");
                String nome=rs.getString("nome");
                String genero=rs.getString("genero");
                String artista=rs.getString("artista");
                String tipo = rs.getString("tipo");
                if (tipo.equals("Video")) return new Video(path,nome,genero);
                return new Musica(rs.getString("path"), rs.getString("nome"),
                        rs.getString("genero"), rs.getString("artista"));
            }
            return null;
        }
        catch (Exception e) {throw new NullPointerException(e.getMessage());}
    }

    public int hashCode() {
        return inst.hashCode();
    }

    public boolean isEmpty() {
        try (Connection conn = DriverManager.getConnection(this.url,this.user,this.pwd)) {
            Statement stm = conn.createStatement();
            ResultSet rs = stm.executeQuery("SELECT nome FROM TMedia");
            return !rs.next();
        }
        catch (Exception e) {throw new NullPointerException(e.getMessage());}
    }

    public void putRelationGenero(String key, Media value, String user){
        try (Connection conn = DriverManager.getConnection(this.url,this.user,this.pwd)) {
            PreparedStatement stm = conn.prepareStatement("DELETE FROM MediaUserGenero " +
                    "WHERE TMedia_path=? AND TUtilizadores_email=?");
            stm.setString(1, key);
            stm.setString(2, user);
            stm.executeUpdate();
            stm = conn.prepareStatement("INSERT INTO MediaUserGenero VALUES (?,?,?)");
            stm.setString(1, key);
            stm.setString(2, value.getGenero());
            stm.setString(3, user);
            stm.executeUpdate();
        }
        catch (Exception e) {throw new NullPointerException(e.getMessage());}
    }

    public void putRelationUp(String key, String user){
        try (Connection conn = DriverManager.getConnection(this.url,this.user,this.pwd)) {
            PreparedStatement stm = conn.prepareStatement("DELETE FROM MediaUser " +
                    "WHERE TMedia_path=? AND TUtilizadores_email=?");
            stm.setString(1,key);
            stm.setString(2,user);
            stm.executeUpdate();
            stm=conn.prepareStatement("INSERT INTO MediaUser VALUES (?,?)");
            stm.setString(1,key);
            stm.setString(2,user);
            stm.executeUpdate();
        }
        catch (Exception e) {throw new NullPointerException(e.getMessage());}
    }

    public Media put(String key, Media value) {
        try (Connection conn = DriverManager.getConnection(this.url,this.user,this.pwd)) {
            PreparedStatement stm = conn.prepareStatement("DELETE FROM TMedia WHERE path=?");
            stm.setString(1,key);
            stm.executeUpdate();
            stm = conn.prepareStatement("INSERT INTO TMedia VALUES (?,?,?,?,?)");
            stm.setString(1,key);
            stm.setString(2,value.getNome());
            stm.setString(3,value.getGenero());
            if (value instanceof Musica) {
                stm.setString(4, ((Musica) value).getArtista());
                stm.setString(5, "Musica");
            }
            else{
                stm.setString(4,"");
                stm.setString(5,"Video");
            }
            stm.executeUpdate();
            if (value instanceof Musica)
                return new Musica(value.getPath(),value.getNome(),value.getGenero(),((Musica)value).getArtista());
            return new Video(value.getPath(),value.getNome(),value.getGenero());
        }
        catch (Exception e) {throw new NullPointerException(e.getMessage());}
    }

    public Media remove(Object key) {
        try (Connection conn = DriverManager.getConnection(this.url,this.user,this.pwd)) {
            Media m = this.get(key);
            PreparedStatement stm = conn.prepareStatement("DELETE FROM TMedia WHERE path=?");
            stm.setString(1,(String)key);
            stm.executeUpdate();
            return m;
        } catch (Exception e) {
            throw new NullPointerException(e.getMessage());
        }
    }

    public void removeInd(String key, String user){
        try (Connection conn = DriverManager.getConnection(this.url,this.user,this.pwd)) {
            Media m = this.get(key);
            PreparedStatement stm = conn.prepareStatement("DELETE FROM MediaUser WHERE TMedia_path=? AND TUtilizadores_email=?");
            stm.setString(1,(String)key);
            stm.setString(2,user);
            stm.executeUpdate();
        } catch (Exception e) {
            throw new NullPointerException(e.getMessage());
        }
    }

    public int size() {
        try (Connection conn = DriverManager.getConnection(this.url,this.user,this.pwd)) {
            int i = 0;
            Statement stm = conn.createStatement();
            ResultSet rs = stm.executeQuery("SELECT nome FROM TMedia");
            for (;rs.next();i++);
            return i;
        }
        catch (Exception e) {throw new NullPointerException(e.getMessage());}
    }

    public Collection<Media> values() {
        try (Connection conn = DriverManager.getConnection(this.url,this.user,this.pwd)) {
            Collection<Media> col = new HashSet<Media>();
            Statement stm = conn.createStatement();
            ResultSet rs = stm.executeQuery("SELECT * FROM TMedia");
            for (;rs.next();) {
                String path=rs.getString("path");
                String nome=rs.getString("nome");
                String genero=rs.getString("genero");
                String artista=rs.getString("artista");
                String tipo=rs.getString("tipo");
                if (tipo.equals("Video")) col.add(new Video(path,nome,genero));
                else col.add(new Musica(rs.getString("path"), rs.getString("nome"),
                        rs.getString("genero"), rs.getString("artista")));
            }
            return col;
        }
        catch (Exception e) {throw new NullPointerException(e.getMessage());}
    }

    public boolean containsValue(Object value) {
        try(Connection conn = DriverManager.getConnection(this.url,this.user,this.pwd)) {
            PreparedStatement stm = conn.prepareStatement("SELECT nome FROM TMedia WHERE path=? " +
                    "AND nome=? AND genero=? AND artista=? AND tipo=?");
            stm.setString(1,((Media)value).getPath());
            stm.setString(2,((Media)value).getNome());
            stm.setString(3,((Media)value).getGenero());
            if (value instanceof Video){
                stm.setString(4,"");
                stm.setString(5,"Video");
            }
            else{
                stm.setString(4,((Musica)value).getArtista());
                stm.setString(5,"Musica");
            }
            ResultSet rs = stm.executeQuery();
            return rs.next();
        }
        catch (Exception e) {throw new NullPointerException(e.getMessage());}
    }

    public Set<Map.Entry<String,Media>> entrySet() {
        try(Connection conn = DriverManager.getConnection(this.url,this.user,this.pwd)){
            Set<Map.Entry<String,Media>> s = new HashSet<>();
            Statement stm = conn.createStatement();
            ResultSet rs = stm.executeQuery("SELECT * FROM TMedia");
            while(rs.next()) {
                String path=rs.getString("path");
                String nome=rs.getString("nome");
                String genero=rs.getString("genero");
                String artista=rs.getString("artista");
                String tipo = rs.getString("tipo");
                Media m;
                if (tipo.equals("Video")) m = new Video(path,nome,genero);
                else m = new Musica(path, nome, genero,artista);
                s.add(new AbstractMap.SimpleEntry<>(rs.getString("path"),m));
            }
            return s;
        }
        catch (Exception e) {throw new NullPointerException(e.getMessage());}
    }

    public boolean equals(Object o) {
        throw new NullPointerException("public boolean equals(Object o) not implemented!");
    }

    public Set<String> keySet() {
        try(Connection conn = DriverManager.getConnection(this.url,this.user,this.pwd)) {
            Set<String> s = new HashSet<>();
            Statement stm = conn.createStatement();
            ResultSet rs = stm.executeQuery("SELECT path FROM TMedia");
            while(rs.next()){
                s.add(rs.getString("path"));
            }
            return s;
        }
        catch (Exception e) {throw new NullPointerException(e.getMessage());}
    }

    public void putAll(Map<? extends String,? extends Media> t) {
        t.forEach(this::put);
    }

    public List<Media> getMediaUser (String email){
        try(Connection conn = DriverManager.getConnection(this.url,this.user,this.pwd)) {
            List<Media> l = new ArrayList<>();
            PreparedStatement stm = conn.prepareStatement("SELECT T.path, T.nome, T.genero, T.artista, T.tipo," +
                    "(SELECT G.genero AS novogen FROM MediaUserGenero AS G WHERE G.TUtilizadores_email=? AND G.TMedia_path=T.path) AS ng FROM TMedia AS T");
            stm.setString(1,email);
            ResultSet rs = stm.executeQuery();
            while(rs.next()){
                String path=rs.getString("path");
                String nome=rs.getString("nome");
                String ng=rs.getString("ng");
                String genero;
                if (ng!=null) genero=ng;
                else genero=rs.getString("genero");
                String artista=rs.getString("artista");
                String tipo = rs.getString("tipo");
                Media m;
                if (tipo.equals("Video")) m = new Video(path,nome,genero);
                else m = new Musica(path,nome,genero, artista);
                l.add(m);
            }
            return l;
        }
        catch (Exception e) {throw new NullPointerException(e.getMessage());}
    }
}