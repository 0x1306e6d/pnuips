package kr.ac.pusan.pnuips;

import org.apache.commons.dbcp2.*;
import org.apache.commons.pool2.ObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPool;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class DatabaseManager {

    private static DataSource dataSource;
    private static boolean setup = false;

    public static Connection getConnection() throws SQLException {
        if (!setup) {
            setup();
        }
        return dataSource.getConnection();
    }

    private synchronized static void setup() throws SQLException {
        if (setup) {
            return;
        }

        try {
            Class.forName(DatabaseConstants.DRIVER);
        } catch (ClassNotFoundException e) {
            throw new SQLException(e);
        }

        ConnectionFactory connectionFactory = new DriverManagerConnectionFactory(
                DatabaseConstants.URL,
                DatabaseConstants.USER,
                DatabaseConstants.PASSWORD);
        PoolableConnectionFactory poolableConnectionFactory = new PoolableConnectionFactory(connectionFactory, null);
        ObjectPool<PoolableConnection> connectionPool = new GenericObjectPool<>(poolableConnectionFactory);
        poolableConnectionFactory.setPool(connectionPool);

        dataSource = new PoolingDataSource<>(connectionPool);
        setup = true;
    }

    private DatabaseManager() {

    }
}
