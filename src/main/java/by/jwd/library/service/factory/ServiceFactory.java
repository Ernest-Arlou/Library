package by.jwd.library.service.factory;


import by.jwd.library.service.UserService;
import by.jwd.library.service.impl.UserServiceImpl;

public final class ServiceFactory {
    private static final ServiceFactory instance = new ServiceFactory();

    private final UserService userService = new UserServiceImpl();

    private ServiceFactory (){
    }

    public static ServiceFactory getInstance (){
        return instance;
    }


    public UserService getUserService(){
        return userService;
    }
}
