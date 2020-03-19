package dao.dao.sqlite;

import dao.DatabaseInitializationDAO;
import dao.DatabaseInitilaizationDAOImpl;
import dao.ExpenseDAO;
import dao.ExpenseDAOImpl;

public class SQLDAOFactory implements IDAOFactory{

    private static SQLDAOFactory instance;

    public static SQLDAOFactory getInstance(){
        if(instance == null){
            instance = new SQLDAOFactory();
        }
        return instance;
    }

    private SQLDAOFactory(){}

    @Override
    public ExpenseDAO getExpenseDAO() {
        return new ExpenseDAOImpl();
    }

    @Override
    public DatabaseInitializationDAO getInitializerDAO() {
        return new DatabaseInitilaizationDAOImpl();
    }
}
