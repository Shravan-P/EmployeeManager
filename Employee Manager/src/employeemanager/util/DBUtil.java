package employeemanager.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DBUtil {

    private static final Logger logger = LogManager.getLogger(DBUtil.class);

    private static final String URL = "jdbc:mysql://localhost:3306/empdb";
    private static final String USER = "root";
    private static final String PASS = "2003";

    public static Connection getConnectionToDB() throws SQLException {
        logger.trace("Attempting to establish database connection to URL: {}", URL);

        try {
            Connection connection = DriverManager.getConnection(URL, USER, PASS);
            logger.info("Database connection established successfully to URL: {}", URL);
            return connection;
        } catch (SQLException e) {
            logger.error("Failed to establish database connection to URL: {}", URL, e);
            throw e; // rethrow so caller handles it
        }
    }
}
