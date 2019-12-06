package postgresql;
import java.sql.*;
public class PSQLConnection {
    /*wifi 192.168.0.107
      cable 192.168.0.103
    * */
    private final String DB_URL = "jdbc:postgresql://127.0.0.1:5432/cinema";
    private final String USER = "postgres";
    private final String PASS = "00000000";

    private Connection connection;

    public PSQLConnection(){
        try {
            connection = (Connection) DriverManager.getConnection(DB_URL,USER,PASS);
        } catch (SQLException e) {
            e.printStackTrace();
            return;
        }
    }


}
