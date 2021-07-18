public class ConnectionInfo {
    private String user;
    private String pwd;
    private String url;
    private String schema;

    public ConnectionInfo(){
        this.schema = "testesclinicos";
        this.url = "jdbc:mysql://localhost/" + this.schema +"?useTimezone=true&serverTimezone=UTC";
        this.user = "root";
        this.pwd = "gunsnroses99";
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

    public String getSchema() {
        return this.schema;
    }
}
