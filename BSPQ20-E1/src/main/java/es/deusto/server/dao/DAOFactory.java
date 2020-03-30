package es.deusto.server.dao;

public class DAOFactory {

    private DAOFactory() {}

    private static DAOFactory instance = new DAOFactory();

    public static DAOFactory getInstance() {
        return instance;
    }

    public UserDAO createUserDAO() {
        return new UserDAO();
    }

    public OrganizerDAO createOrganizerDAO() {
        return new OrganizerDAO();
    }

}