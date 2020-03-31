package by.jwd.registration.service;

import by.jwd.registration.bean.LoginInfo;
import by.jwd.registration.bean.User;

public interface LibraryService {
    User getUser (String login) throws ServiceException;

    String login (LoginInfo loginInfo) throws ServiceException;

    String register (User user) throws ServiceException;
}
