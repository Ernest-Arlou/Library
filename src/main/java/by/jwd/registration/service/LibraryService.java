package by.jwd.registration.service;

import by.jwd.registration.bean.LoginInfo;
import by.jwd.registration.bean.RegistrationInfo;

public interface LibraryService {
    String login (LoginInfo loginInfo) throws ServiceException;

    String register (RegistrationInfo registrationInfo) throws ServiceException;
}
