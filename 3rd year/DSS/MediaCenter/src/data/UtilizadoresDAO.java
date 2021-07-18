package data;

import ConnectInfo.ConnectInfo;
import business.Utilizador.*;

import java.sql.*;
import java.util.*;

public class UtilizadoresDAO implements Map<String, Utilizador> {
    private static UtilizadoresDAO inst = null;
    private ConnectInfo con = new ConnectInfo();
    private String url  = con.getUrl();
    private String user = con.getUser();
    private String pwd  = con.getPwd();

    public UtilizadoresDAO() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            createDatabase();
            createTable();
        }
        catch (ClassNotFoundException e) {
            throw new NullPointerException(e.getMessage());
        }
    }

    public void createDatabase(){
        try (Connection conn = DriverManager.getConnection(this.con.getNewSchemaURL(),this.user,this.pwd)){
            Statement stm = conn.createStatement();
            stm.executeUpdate("CREATE SCHEMA IF NOT EXISTS mediacenter");
        }
        catch (Exception e) {throw new NullPointerException(e.getMessage());}
    }

    public static UtilizadoresDAO getInstance() {
        if (inst == null) {
            inst = new UtilizadoresDAO();
        }
        return inst;
    }

    public void createTable(){
        try (Connection conn = DriverManager.getConnection(this.url,this.user,this.pwd)){
            Statement stm = conn.createStatement();
            String sql = "CREATE TABLE IF NOT EXISTS mediacenter.TUtilizadores (" +
                    "email VARCHAR(255) NOT NULL," +
                    "pwd VARCHAR(255) NOT NULL," +
                    "nome VARCHAR(255) NOT NULL," +
                    "tipo VARCHAR(255) NOT NULL," +
                    "PRIMARY KEY (email)," +
                    "UNIQUE INDEX email_UNIQUE (email ASC) VISIBLE)";
            stm.executeUpdate(sql);
        }
        catch (Exception e) {throw new NullPointerException(e.getMessage());}
    }

    public void clear () {
        try (Connection conn = DriverManager.getConnection(this.url,this.user,this.pwd)){
            Statement stm = conn.createStatement();
            stm.executeUpdate("DELETE FROM TUtilizadores");
        }
        catch (Exception e) {throw new NullPointerException(e.getMessage());}
    }

    public boolean containsKey(Object key) throws NullPointerException {
        try (Connection conn = DriverManager.getConnection(this.url,this.user,this.pwd)){
            Statement stm = conn.createStatement();
            String sql = "SELECT nome FROM TUtilizadores WHERE email='"+(String)key+"'";
            ResultSet rs = stm.executeQuery(sql);
            return rs.next();
        }
        catch (Exception e) {throw new NullPointerException(e.getMessage());}
    }

    // Adicionar entrada na tabela a identificar admin ou user
    public Utilizador get(Object key) {
        try (Connection conn = DriverManager.getConnection(this.url,this.user,this.pwd)){
            Statement stm = conn.createStatement();
            String sql = "SELECT * FROM TUtilizadores WHERE email='"+(String)key+"'";
            ResultSet rs = stm.executeQuery(sql);
            if (rs.next())
                if (rs.getString("tipo").equals("User"))
                    return new User(rs.getString("email"),rs.getString("pwd"),rs.getString("nome"));
                else return new Admin (rs.getString("email"),rs.getString("pwd"),rs.getString("nome"));
            return null;
        }
        catch (Exception e) {throw new NullPointerException(e.getMessage());}
    }

    public int hashCode() {
        return inst.hashCode();
    }

    public boolean isEmpty() {
        try (Connection conn = DriverManager.getConnection(this.url,this.user,this.pwd)){
            Statement stm = conn.createStatement();
            ResultSet rs = stm.executeQuery("SELECT nome FROM TUtilizadores");
            return !rs.next();
        }
        catch (Exception e) {throw new NullPointerException(e.getMessage());}
    }

    public Utilizador put(String key, Utilizador value) {
        try (Connection conn = DriverManager.getConnection(this.url,this.user,this.pwd)) {
            PreparedStatement stm;
            if (containsKey(key)) {
                stm = conn.prepareStatement("UPDATE TUtilizadores SET email=?,pwd=?,nome=?,tipo=? WHERE email=?");
                stm.setString(1, value.getEmail());
                stm.setString(2, value.getPassword());
                stm.setString(3, value.getNome());
                if (value instanceof User) {
                    stm.setString(4, "User");
                    stm.setString(5, key);
                    stm.executeUpdate();
                    return new User(value.getEmail(), value.getPassword(), value.getNome());
                }
                else{
                    stm.setString(4, "Admin");
                    stm.setString(5, key);
                    stm.executeUpdate();
                    return new Admin(value.getEmail(), value.getPassword(), value.getNome());
                }
            }
            else {
                stm = conn.prepareStatement("INSERT INTO TUtilizadores VALUES (?,?,?,?)");
                stm.setString(1, key);
                stm.setString(2, value.getPassword());
                stm.setString(3, value.getNome());
                if (value instanceof User) {
                    stm.setString(4, "User");
                    stm.executeUpdate();
                    return new User(value.getEmail(), value.getPassword(), value.getNome());
                }
                else{
                    stm.setString(4, "Admin");
                    stm.executeUpdate();
                    return new Admin(value.getEmail(), value.getPassword(), value.getNome());
                }
            }
        }
        catch (Exception e) {throw new NullPointerException(e.getMessage());}
    }

    public Utilizador remove(Object key) {
        try (Connection conn = DriverManager.getConnection(this.url,this.user,this.pwd)){
            Utilizador u = this.get(key);
            PreparedStatement stm = conn.prepareStatement("DELETE FROM TUtilizadores WHERE email=?");
            stm.setString(1,(String)key);
            stm.executeUpdate();
            return u;
        } catch (Exception e) {
            throw new NullPointerException(e.getMessage());
        }
    }

    public int size() {
        try (Connection conn = DriverManager.getConnection(this.url,this.user,this.pwd)){
            int i = 0;
            Statement stm = conn.createStatement();
            ResultSet rs = stm.executeQuery("SELECT nome FROM TUtilizadores");
            for (;rs.next();i++);
            return i;
        }
        catch (Exception e) {throw new NullPointerException(e.getMessage());}
    }

    public Collection<Utilizador> values() {
        try (Connection conn = DriverManager.getConnection(this.url,this.user,this.pwd)){
            Collection<Utilizador> col = new HashSet<Utilizador>();
            Statement stm = conn.createStatement();
            ResultSet rs = stm.executeQuery("SELECT * FROM TUtilizadores");
            for (;rs.next();) {
                Utilizador u;
                if (rs.getString("tipo").equals("User"))
                    u = new User(rs.getString("email"),rs.getString("pwd"),rs.getString("nome"));
                else u = new Admin(rs.getString("email"),rs.getString("pwd"),rs.getString("nome"));
                col.add(u);
            }
            return col;
        }
        catch (Exception e) {throw new NullPointerException(e.getMessage());}
    }

    public boolean containsValue(Object value) {
        try (Connection conn = DriverManager.getConnection(this.url,this.user,this.pwd)){
            PreparedStatement stm = conn.prepareStatement("SELECT nome FROM TUtilizadores WHERE email=?, pwd=?, nome=?, tipo=?");
            stm.setString(1,((Utilizador)value).getEmail());
            stm.setString(2,((Utilizador)value).getPassword());
            stm.setString(3,((Utilizador)value).getNome());
            if (value instanceof User)
                stm.setString(4,"User");
            else stm.setString(4,"Admin");
            ResultSet rs = stm.executeQuery();
            return rs.next();
        }
        catch (Exception e) {throw new NullPointerException(e.getMessage());}
    }

    public Set<Map.Entry<String,Utilizador>> entrySet() {
        try (Connection conn = DriverManager.getConnection(this.url,this.user,this.pwd)){
            Set<Map.Entry<String,Utilizador>> s = new HashSet<>();
            Statement stm = conn.createStatement();
            ResultSet rs = stm.executeQuery("SELECT * FROM TUtilizadores");
            while(rs.next()) {
                Utilizador u;
                if (rs.getString("tipo").equals("User"))
                    u = new User(rs.getString("email"),rs.getString("pwd"),rs.getString("nome"));
                else u = new Admin(rs.getString("email"),rs.getString("pwd"),rs.getString("nome"));
                s.add(new AbstractMap.SimpleEntry<>(rs.getString("email"),u));
            }
            return s;
        }
        catch (Exception e) {throw new NullPointerException(e.getMessage());}
    }

    public boolean equals(Object o) {
        throw new NullPointerException("public boolean equals(Object o) not implemented!");
    }

    public Set<String> keySet() {
        try (Connection conn = DriverManager.getConnection(this.url,this.user,this.pwd)){
            Set<String> s = new HashSet<>();
            Statement stm = conn.createStatement();
            ResultSet rs = stm.executeQuery("SELECT email FROM TUtilizadores");
            while(rs.next()){
                s.add(rs.getString("email"));
            }
            return s;
        }
        catch (Exception e) {throw new NullPointerException(e.getMessage());}
    }

    public void putAll(Map<? extends String,? extends Utilizador> t) {
        t.forEach(this::put);
    }
}