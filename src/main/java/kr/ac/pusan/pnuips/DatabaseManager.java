package kr.ac.pusan.pnuips;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseManager {

    public static Connection getConnection() throws SQLException {
        try {
            Class.forName(DatabaseConstants.DRIVER);
        } catch (ClassNotFoundException e) {
            throw new SQLException(e);
        }

        return DriverManager.getConnection(
                DatabaseConstants.URL,
                DatabaseConstants.USER,
                DatabaseConstants.PASSWORD
        );
    }

    private DatabaseManager() {

    }
}
