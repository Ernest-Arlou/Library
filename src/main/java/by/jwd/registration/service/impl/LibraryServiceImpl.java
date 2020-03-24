package by.jwd.registration.service.impl;

import by.jwd.registration.bean.LoginInfo;
import by.jwd.registration.bean.RegistrationInfo;
import by.jwd.registration.dao.DAOException;
import by.jwd.registration.dao.factory.DAOFactory;
import by.jwd.registration.service.LibraryService;
import by.jwd.registration.service.ServiceException;

public class LibraryServiceImpl implements LibraryService {
    @Override
    public String login (LoginInfo loginInfo) throws ServiceException{
        try {
            return DAOFactory.getInstance().getLibraryDAO().loginUser(loginInfo);
        } catch (DAOException e) {
            throw new ServiceException("Error in login",e);
        }
    }

    @Override
    public String register (RegistrationInfo registrationInfo) throws ServiceException{
        try {
            return DAOFactory.getInstance().getLibraryDAO().registerUser(registrationInfo);
        } catch (DAOException e) {
            throw new ServiceException("Error in registration",e);
        }
    }
}
