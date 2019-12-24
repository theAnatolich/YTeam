package postgresql;
import java.sql.*;
import java.util.Random;

public class PSQLConnection {

    /*wifi 192.168.0.107
          cable 192.168.0.103
        * */
    private static String DB_URL = "jdbc:postgresql://192.168.0.103:64000/cinema";
    private static String USER = "pg";
    private static String PASS = "12345678";

    public Connection getConnection() {
        return connection;
    }
    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    private Connection connection;
    private static Connection connectionST;

    static {
        try {
            connectionST = (Connection) DriverManager.getConnection(DB_URL,USER,PASS);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    ;

    public PSQLConnection(){
        try {
            connection = (Connection) DriverManager.getConnection(DB_URL,USER,PASS);
        } catch (SQLException e) {
            e.printStackTrace();
            return;
        }
    }

    public static boolean checkOperation(int op_id) throws SQLException {
        Statement stat=connectionST.createStatement();
        String qu="select state from operation where id ="+op_id;
        ResultSet rs= stat.executeQuery(qu);
        rs.next();
        if(rs.getInt(1)==3) return true;
        return false;
    }

    public static void errTicBuy(int op_id) throws SQLException {
        Statement stat=connectionST.createStatement();
        String qu="select return_ticket("+op_id+")";
        ResultSet rs= stat.executeQuery(qu);
    }

    public static void purchaseConfirmation(int op_id) throws SQLException {
        Statement stat=connectionST.createStatement();
        String qu="update operation set state=3 where  id="+op_id;
        stat.executeUpdate(qu);
    }



}
