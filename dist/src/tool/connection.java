package tool;
import com.mysql.jdbc.Driver;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class connection {
    public static Connection coneksi;
    public static Connection GetConnection()throws SQLException{
        if (coneksi==null){
            new Driver();
            coneksi=DriverManager.getConnection("jdbc:mysql://localhost:3306/kioswma","root","");
        }
        return coneksi;
    }
}