package service;

import service.repository.ClientDAO;
import service.repository.SimpleJdbcDAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ClientService implements AutoCloseable {

    private final Connection connection;
    private final ClientDAO dao;

    public ClientService(String dbUrl) {
        connectDriver();
        this.connection = createConnection(dbUrl);
        this.dao = new SimpleJdbcDAO(connection);
    }

    public void createPhones(int count) throws SQLException {
        PreparedStatement ps = connection.prepareStatement("INSERT INTO Phone (PHONE_TYPE, PHONE_NUMBER, CLIENT_ID) " +
                "VALUES ('Work', ?, 3)");

        connection.setAutoCommit(false);

        try {
            for (int i = 0; i < count; i++) {
                ps.setString(1, String.format("777-%d", i));
                ps.addBatch();
            }
            ps.executeBatch();
            connection.commit();
        } catch (SQLException e) {
            connection.rollback();
        } finally {
            connection.setAutoCommit(true);
        }
    }


    public ClientDAO getDao() {
        return dao;
    }

    private Connection createConnection(String dbUrl) {
        try {
            return DriverManager.getConnection("jdbc:sqlite:" + dbUrl);
        } catch (SQLException e) {
            throw new IllegalArgumentException("Invalid database URL: " + dbUrl);
        }
    }

    private void connectDriver() {
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Failed to find JDBC driver", e);
        }
    }

    @Override
    public void close() throws Exception {
        if (connection != null) {
            connection.close();
        }
    }
}
