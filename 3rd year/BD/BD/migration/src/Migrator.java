import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;

public class Migrator {

    public static void createCSV(Connection conn, String tableName, ConnectionInfo con){
        try {
            FileWriter f = new FileWriter(System.getProperty("java.io.tmpdir") + tableName + ".csv");
            Statement  stm = conn.createStatement();
            String query = "SELECT COLUMN_NAME FROM INFORMATION_SCHEMA.COLUMNS " +
                    "WHERE TABLE_SCHEMA="+ "'" + con.getSchema() + "'" +" AND TABLE_NAME=" + "'" + tableName + "'" +
                    " ORDER BY ORDINAL_POSITION ASC";
            ResultSet rs = stm.executeQuery(query);

            rs.last();
            int tamanho = rs.getRow();
            rs.first();

            for(int i=0; i<tamanho; rs.next(), i++){
                f.append(String.valueOf(rs.getNString(1)));
                if (i != (tamanho - 1)) {
                    f.append(',');
                }
            }
            f.append('\n');

            System.out.println(tableName + " CSV File was created!");

            stm = conn.createStatement();
            rs = stm.executeQuery("SELECT * FROM " + tableName);

            rs.last();
            int size = rs.getRow();
            rs.first();

            for(int j=1; j <= size; j++, rs.next()){
                for(int i=1; i <= tamanho; i++) {
                    f.append(String.valueOf(rs.getObject(i).toString()));
                    if (i != tamanho){
                        f.append(",");
                    }
                    else if (j != size){
                        f.append("\n");
                    }
                }
            }
            f.flush();
            f.close();
        }
        catch (SQLException | IOException e){
            e.printStackTrace();
        }
    }

    public static void main(String[] args){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            ConnectionInfo con = new ConnectionInfo();
            Connection conn = DriverManager.getConnection(con.getUrl(), con.getUser(), con.getPwd());

            Statement  stm = conn.createStatement();
            ResultSet rs = stm.executeQuery("SELECT TABLE_NAME FROM INFORMATION_SCHEMA.TABLES WHERE table_schema=" + "'" + con.getSchema() + "'");
            rs.last();
            int size = rs.getRow();
            rs.first();

            for (int i=1; i<=size; i++, rs.next()){
                String tablename = String.valueOf(rs.getNString(1));
                if (!tablename.startsWith("view"))
                    createCSV(conn,tablename, con);
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }
    }
}
