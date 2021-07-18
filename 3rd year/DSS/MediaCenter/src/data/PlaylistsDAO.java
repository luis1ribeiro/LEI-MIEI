package data;

import ConnectInfo.ConnectInfo;
import business.Playlist.*;
import business.Media.*;

import java.sql.*;
import java.util.*;

public class PlaylistsDAO implements Map<String, Playlist> {
    private static PlaylistsDAO inst = null;
    private ConnectInfo con = new ConnectInfo();
    private String url  = con.getUrl();
    private String user = con.getUser();
    private String pwd  = con.getPwd();

    public PlaylistsDAO() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            createDatabase();
            createTable();
        }
        catch (ClassNotFoundException e) {
            throw new NullPointerException(e.getMessage());
        }
    }

    public static PlaylistsDAO getInstance() {
        if (inst == null) {
            inst = new PlaylistsDAO();
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

    public void createTable(){
        try (Connection conn = DriverManager.getConnection(this.url,this.user,this.pwd)){
            Statement stm = conn.createStatement();
            String sql = "CREATE TABLE IF NOT EXISTS mediacenter.TPlaylists (" +
                    "nome VARCHAR(255) NOT NULL," +
                    "TUtilizadores_email VARCHAR(255) NOT NULL," +
                    "PRIMARY KEY (nome, TUtilizadores_email)," +
                    "INDEX fk_TPlaylist_TUtilizadores1_idx (TUtilizadores_email ASC) VISIBLE," +
                    "CONSTRAINT fk_TPlaylist_TUtilizadores1 " +
                    "FOREIGN KEY (TUtilizadores_email) " +
                    "REFERENCES mediacenter.TUtilizadores (email) " +
                    "ON DELETE CASCADE " +
                    "ON UPDATE CASCADE)";
            stm.executeUpdate(sql);
            sql = "CREATE TABLE IF NOT EXISTS mediacenter.MediaPlaylistUser (" +
                    "TMedia_path VARCHAR(255) NOT NULL," +
                    "TPlaylist_nome VARCHAR(255) NOT NULL," +
                    "TPlaylist_TUtilizadores_email VARCHAR(255) NOT NULL," +
                    "PRIMARY KEY (TMedia_path, TPlaylist_nome, TPlaylist_TUtilizadores_email)," +
                    "INDEX fk_TMedia_has_TPlaylist_TPlaylist1_idx (TPlaylist_nome ASC, TPlaylist_TUtilizadores_email ASC) VISIBLE," +
                    "INDEX fk_TMedia_has_TPlaylist_TMedia1_idx (TMedia_path ASC) VISIBLE," +
                    "CONSTRAINT fk_TMedia_has_TPlaylist_TMedia1 " +
                    "FOREIGN KEY (TMedia_path) " +
                    "REFERENCES mediacenter.TMedia (path) " +
                    "ON DELETE CASCADE " +
                    "ON UPDATE CASCADE, " +
                    "CONSTRAINT fk_TMedia_has_TPlaylist_TPlaylist1 " +
                    "FOREIGN KEY (TPlaylist_nome , TPlaylist_TUtilizadores_email) " +
                    "REFERENCES mediacenter.TPlaylists (nome , TUtilizadores_email) " +
                    "ON DELETE CASCADE " +
                    "ON UPDATE CASCADE)";
            stm.executeUpdate(sql);
        }
        catch (Exception e) {throw new NullPointerException(e.getMessage());}
    }

    public void clear () {
        try (Connection conn = DriverManager.getConnection(this.url,this.user,this.pwd)) {
            Statement stm = conn.createStatement();
            stm.executeUpdate("DELETE FROM TPlaylists");
        }
        catch (Exception e) {throw new NullPointerException(e.getMessage());}
    }

    public boolean containsKey(Object key) throws NullPointerException {
        try (Connection conn = DriverManager.getConnection(this.url,this.user,this.pwd)) {
            PreparedStatement stm = conn.prepareStatement("SELECT nome FROM TPlaylists WHERE nome=?");
            stm.setString(1,(String)key);
            ResultSet rs = stm.executeQuery();
            return rs.next();
        }
        catch (Exception e) {throw new NullPointerException(e.getMessage());}
    }


    public Playlist get(Object key) {
        try (Connection conn = DriverManager.getConnection(this.url,this.user,this.pwd)) {
            PreparedStatement stm = conn.prepareStatement("SELECT * FROM TPlaylists WHERE nome=?");
            stm.setString(1,(String)key);
            ResultSet rs = stm.executeQuery();
            if (rs.next())
                return new Playlist(rs.getString("nome"),rs.getString("TUtilizadores_email"));
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
            ResultSet rs = stm.executeQuery("SELECT nome FROM TPlaylists");
            return !rs.next();
        }
        catch (Exception e) {throw new NullPointerException(e.getMessage());}
    }

    public void putRelation(Media m, Playlist p){
        try (Connection conn = DriverManager.getConnection(this.url,this.user,this.pwd)){
            PreparedStatement stm = conn.prepareStatement("DELETE FROM MediaPlaylistUser WHERE TMedia_path=? " +
                    "AND TPlaylist_nome=? AND TPlaylist_TUtilizadores_email=?");
            stm.setString(1,m.getPath());
            stm.setString(2,p.getNome());
            stm.setString(3,p.getUser());
            stm.executeUpdate();
            stm = conn.prepareStatement("INSERT INTO MediaPlaylistUser VALUES (?,?,?)");
            stm.setString(1,m.getPath());
            stm.setString(2,p.getNome());
            stm.setString(3,p.getUser());
            stm.executeUpdate();
        }
        catch (Exception e) {throw new NullPointerException(e.getMessage());}
    }

    public List<String> getMediaPlaylist (String nome,String user){
        try (Connection conn = DriverManager.getConnection(this.url,this.user,this.pwd)){
            PreparedStatement stm = conn.prepareStatement("SELECT * FROM MediaPlaylistUser WHERE TPlaylist_nome=? AND TPlaylist_TUtilizadores_email=?");
            stm.setString(1,nome);
            stm.setString(2,user);
            List<String> list = new ArrayList<>();
            ResultSet rs = stm.executeQuery();
            for (;rs.next();) {
                list.add(rs.getString("TMedia_path"));
            }
            return list;
        }catch (Exception e) {throw new NullPointerException(e.getMessage());}
    }

    public Playlist put(String key, Playlist value) {
        try (Connection conn = DriverManager.getConnection(this.url,this.user,this.pwd)) {
            PreparedStatement stm = conn.prepareStatement("DELETE FROM TPlaylists WHERE nome=? AND TUtilizadores_email=?");
            stm.setString(1,key);
            stm.setString(2,value.getUser());
            stm.executeUpdate();
            stm=conn.prepareStatement("INSERT INTO TPlaylists VALUES (?,?)");
            stm.setString(1,key);
            stm.setString(2,value.getUser());
            stm.executeUpdate();
            return new Playlist(key,value.getUser());
        }
        catch (Exception e) {throw new NullPointerException(e.getMessage());}
    }

    public Playlist remove(Object key) {
        try (Connection conn = DriverManager.getConnection(this.url,this.user,this.pwd)) {
            Playlist p = this.get(key);
            Statement stm = conn.createStatement();
            String sql = "DELETE '" + key + "' FROM TPlaylists";
            stm.executeUpdate(sql);
            return p;
        } catch (Exception e) {
            throw new NullPointerException(e.getMessage());
        }
    }

    public int size() {
        try (Connection conn = DriverManager.getConnection(this.url,this.user,this.pwd)) {
            int i = 0;
            Statement stm = conn.createStatement();
            ResultSet rs = stm.executeQuery("SELECT nome FROM TPlaylists");
            for (;rs.next();i++);
            return i;
        }
        catch (Exception e) {throw new NullPointerException(e.getMessage());}
    }

    public Collection<Playlist> values() {
        try (Connection conn = DriverManager.getConnection(this.url,this.user,this.pwd)) {
            Collection<Playlist> col = new HashSet<Playlist>();
            Statement stm = conn.createStatement();
            ResultSet rs = stm.executeQuery("SELECT * FROM TPlaylists");
            for (;rs.next();) {
                col.add(new Playlist(rs.getString("nome"),rs.getString("TUtilizadores_email")));
            }
            return col;
        }
        catch (Exception e) {throw new NullPointerException(e.getMessage());}
    }

    public boolean containsValue(Object value) {
        try(Connection conn = DriverManager.getConnection(this.url,this.user,this.pwd)) {
            PreparedStatement stm = conn.prepareStatement("SELECT nome FROM TUtilizadores WHERE nome=? AND TUtilizadores_email=?");
            stm.setString(1,((Playlist)value).getNome());
            stm.setString(1,((Playlist)value).getUser());
            ResultSet rs = stm.executeQuery();
            return rs.next();
        }
        catch (Exception e) {throw new NullPointerException(e.getMessage());}
    }

    public Set<Map.Entry<String,Playlist>> entrySet() {
        try(Connection conn = DriverManager.getConnection(this.url,this.user,this.pwd)){
            Set<Map.Entry<String,Playlist>> s = new HashSet<>();
            Statement stm = conn.createStatement();
            ResultSet rs = stm.executeQuery("SELECT * FROM TPlaylists");
            while(rs.next()) {
                Playlist p = new Playlist(rs.getString("nome"),rs.getString("TUtilizadores_email"));
                s.add(new AbstractMap.SimpleEntry<>(rs.getString("nome"),p));
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
            ResultSet rs = stm.executeQuery("SELECT nome FROM TPlaylists");
            while(rs.next()){
                s.add(rs.getString("nome"));
            }
            return s;
        }
        catch (Exception e) {throw new NullPointerException(e.getMessage());}
    }

    public void putAll(Map<? extends String,? extends Playlist> t) {
        t.forEach(this::put);
    }
}