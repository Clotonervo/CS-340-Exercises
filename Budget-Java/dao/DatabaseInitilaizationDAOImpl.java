package dao;

import database.ConnectionFactory;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseInitilaizationDAOImpl implements DatabaseInitializationDAO {

    private final String BUDGET_CREATE_STATEMENT =
            "create table if not exists budgets ("
                    + "id text not null primary key,"
                    + "month text not null,"
                    + "year integer not null,"
                    + "monthly_allowance real not null"
                    + " )";
    private final String INCOME_CREATE_STATEMENT =
            "create table if not exists incomes ("
                    + "id text not null primary key,"
                    + "projected real,"
                    + "actual real"
                    + " )";
    private final String EXPENSE_CREATE_STATEMENT =
            "create table if not exists expenses ("
                    + "id text not null primary key,"
                    + "month text not null,"
                    + "day integer not null,"
                    + "year integer not null,"
                    + "name text not null,"
                    + "amount real not null,"
                    + "budget_id text not null"
                    + " )";

    @Override
    public boolean initializeDatabase() {

        boolean databaseInitialized = false;
        Connection connection = ConnectionFactory.connection();

        try (Statement stmt = connection.createStatement()) {
            stmt.executeUpdate(BUDGET_CREATE_STATEMENT);
            stmt.executeUpdate(INCOME_CREATE_STATEMENT);
            stmt.executeUpdate(EXPENSE_CREATE_STATEMENT);

            ConnectionFactory.closeConnection(true);

            databaseInitialized = true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return databaseInitialized;
    }
}
