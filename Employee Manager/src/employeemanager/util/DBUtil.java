package employeemanager.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBUtil {

	
    private static final String URL = "jdbc:mysql://localhost:3306/empdb";
    private static final String USER = "root";
    private static final String PASS = "2003";

    public static Connection getConnectionToDB() {
        try {
        	
            return DriverManager.getConnection(URL, USER, PASS);
        } 
        catch (SQLException e) {
            
            return null;
        }
    }
}
