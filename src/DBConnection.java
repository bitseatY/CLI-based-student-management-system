import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    private Connection connection;
    private  String url;
    private  String password;
    private  String username;
    public    DBConnection(String url,String username,String password){
        this.url=url;
        this.password=password;
        this.username=username;
    }



    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url,username,password);

    }
}
