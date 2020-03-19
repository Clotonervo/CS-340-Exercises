import dao.DatabaseInitializationDAO;
import dao.DatabaseInitilaizationDAOImpl;
import dao.dao.sqlite.IDAOFactory;
import dao.dao.sqlite.SQLDAOFactory;
import database.ConnectionFactory;
import view.Navigator;
import view.main.MainView;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class Main {

    public static void main(String[] args) {

        IDAOFactory daoFactory = SQLDAOFactory.getInstance();
        DatabaseInitializationDAO databaseInitializationDAO = daoFactory.getInitializerDAO();
        boolean databaseInitialized = databaseInitializationDAO.initializeDatabase();

        if (databaseInitialized) {
            System.out.println("database initialized");
            Navigator.push(MainView.class, null);
        }
    }
}
