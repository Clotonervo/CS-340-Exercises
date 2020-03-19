package dao.dao.sqlite;

public class DAOFactory {
    public static IDAOFactory getDaoFactory(){
        return SQLDAOFactory.getInstance();
    }
}
