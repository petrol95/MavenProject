package HomeWork2;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Shop implements AutoCloseable {

    private final Connection connection;

    public Shop(String dbUrl) {
        connectDriver();
        this.connection = createConnection(dbUrl);
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

    public List<Good> getAllGoods() {
        List<Good> result = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM Shop");
            while (resultSet.next()) {
                Good good = createGood(resultSet);
                result.add(good);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to execute query", e);
        }
        return result;
    }

    private Good createGood(ResultSet resultSet) throws SQLException {
        int goodId = resultSet.getInt(1);
        int productId = resultSet.getInt(2);
        String title = resultSet.getString(3);
        int cost = resultSet.getInt(4);

        Good good = new Good();
        good.setId(goodId);
        good.setProductID(productId);
        good.setTitle(title);
        good.setCost(cost);
        return good;
    }

    public void createGoods(int count) throws SQLException {
        PreparedStatement ps = connection.prepareStatement("INSERT INTO Shop (PRODID, TITLE, COST) " +
                "VALUES (?, ?, ?)");

        connection.setAutoCommit(false);

        this.emptyTable();

        try {
            for (int i = 0; i < count; i++) {
                ps.setString(1, String.format("%d", i + 1));
                ps.setString(2, String.format("good%d", i + 1));
                ps.setString(3, String.format("%d", 10 + i));
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

    public void createTable() {
        try {
            PreparedStatement statement = connection.prepareStatement(
                    "CREATE TABLE IF NOT EXISTS Shop\n" +
                    "(\n" +
                    "    ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,\n" +
                    "    PRODID INTEGER NOT_NULL,\n" +
                    "    TITLE TEXT NOT_NULL,\n" +
                    "    COST INTEGER NOT_NULL\n" +
                    ");");
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void emptyTable() {
        try {
            PreparedStatement statement = connection.prepareStatement("DELETE FROM Shop");
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void getCostByTitle(String title) {
        int cost = 0;
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT COST FROM Shop WHERE TITLE = '" + title + "'");
            while (resultSet.next()) {
                cost = resultSet.getInt("COST");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to execute query", e);
        }
        if (cost == 0) {
            System.out.println("No such good");
        } else {
            System.out.println("Price = " + cost);
        }
    }

    public void changeCostByTitle(String title, int newCost) {
        try {
            PreparedStatement statement = connection.prepareStatement("UPDATE Shop SET COST = '" + newCost +
                    "' WHERE TITLE = '" + title + "'");
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public List<Good> getGoodsByRange(int costFrom, int costTo) {
        List<Good> result = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM Shop " +
                    "WHERE COST BETWEEN '" + costFrom +
                    "' AND '" + costTo + "'");
            while (resultSet.next()) {
                Good good = createGood(resultSet);
                result.add(good);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to execute query", e);
        }
        return result;
    }
}
