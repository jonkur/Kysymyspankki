package kysymyspankki.database;

import java.sql.*;

/**
 *
 * @author jonask
 */
public class Database {
    private final String dbAddress;
    
    public Database(String dbAddress) {
        this.dbAddress = dbAddress;
    }
    
    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(dbAddress);
    }
    
}
