package ConnectInfo;

public class ConnectInfo {

    private String url;
    private String user;
    private String pwd;
    private String newSchemaURL;

    public ConnectInfo(){
        this.url = "jdbc:mysql://localhost/mediacenter?useTimezone=true&serverTimezone=UTC";
        this.user = "root";
        this.pwd = "gunsnroses99";
        this.newSchemaURL="jdbc:mysql://localhost?useTimezone=true&serverTimezone=UTC";
    }

    public String getUrl() {
        return this.url;
    }

    public String getUser() {
        return this.user;
    }

    public String getPwd() {
        return this.pwd;
    }

    public String getNewSchemaURL() {
        return newSchemaURL;
    }
}
