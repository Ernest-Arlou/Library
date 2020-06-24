package by.jwd.library.service;

import by.jwd.library.bean.LoginInfo;
import by.jwd.library.bean.User;
import by.jwd.library.dao.DAOException;

import java.util.List;

public interface UserService {
    User getUserByLogin(String login) throws ServiceException;

    User getUserById(int userId) throws ServiceException;

    User getUserByEmail(String email) throws ServiceException;

    User login(LoginInfo loginInfo) throws ServiceException;

    boolean loginExists(String login) throws ServiceException;

    boolean emailExists(String email) throws ServiceException;

    boolean passportIdExists(String passportId) throws ServiceException;

    List<User> getUnverifiedUsers() throws ServiceException;

    void editUser(User user) throws ServiceException;

    void register(User user) throws ServiceException;

    //    public void editUserPassportId (int userId, String passportId){
//        try {
//            User user = DAOFactory.getInstance().getUserDAO().getUserById(userId);
//            user
//
//        } catch (DAOException e) {
//            e.printStackTrace();
//        }
//    }
    void changePassportId(int userId, String passportId) throws ServiceException;

    //    public void editUserPassportId (int userId, String passportId){
    //        try {
    //            User user = DAOFactory.getInstance().getUserDAO().getUserById(userId);
    //            user
    //
    //        } catch (DAOException e) {
    //            e.printStackTrace();
    //        }
    //    }
    List<User> searchUnverifiedUsers(String searchStr) throws ServiceException;

    //    public void editUserPassportId (int userId, String passportId){
    //        try {
    //            User user = DAOFactory.getInstance().getUserDAO().getUserById(userId);
    //            user
    //
    //        } catch (DAOException e) {
    //            e.printStackTrace();
    //        }
    //    }
    void verifyUser(int userId) throws ServiceException;
}
