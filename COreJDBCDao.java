import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class COreJDBCDao {
    protected Connection connection;
    COreJDBCDao(){
        try {
            String url = "jdbc:mariadb://localhost:3306/hw1";
            String user = "root";
            String password = "root";
            connection = DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            System.err.println("Connection cannot be established");
            e.printStackTrace();
        }
    }

    public void closeConnection(){
        try {
            connection.close();
        } catch (SQLException e) {
            System.err.println("Connection cannot be closed");
        }
    }
}