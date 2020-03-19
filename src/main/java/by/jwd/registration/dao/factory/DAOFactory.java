//package by.jwd.registration.dao.factory;
//
//import by.jwd.registration.dao.connectionpool.ConnectionPool;
//import by.jwd.registration.dao.connectionpool.ConnectionPoolException;
//
//public final class DAOFactory {
//    private final ConnectionPool connectionPool;
//
//
//
//    private final BookDAO bookDAO = new FileBookDAO();
//    private final UserDAO userDAO = new FileUserDAO();
//
//    private DAOFactory () throws ConnectionPoolException{
//        connectionPool = new ConnectionPool();
//        connectionPool.initPoolData();
//    }
//
//    public static DAOFactory getInstance (){
//        return instance;
//    }
//
//    public BookDAO getBookDAO (){
//        return bookDAO;
//    }
//
//    public UserDAO getUserDAO (){
//        return userDAO;
//    }
//}