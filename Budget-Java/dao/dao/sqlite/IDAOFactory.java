package dao.dao.sqlite;

import dao.DatabaseInitializationDAO;
import dao.ExpenseDAO;

public interface IDAOFactory {
    ExpenseDAO getExpenseDAO();
    DatabaseInitializationDAO getInitializerDAO();
}
