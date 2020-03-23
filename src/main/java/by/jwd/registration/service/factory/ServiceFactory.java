package by.jwd.registration.service.factory;


import by.jwd.registration.service.LibraryService;
import by.jwd.registration.service.impl.LibraryServiceImpl;

public final class ServiceFactory {
    private static final ServiceFactory instance = new ServiceFactory();

    private final LibraryService libraryService = new LibraryServiceImpl();

    private ServiceFactory (){
    }

    public static ServiceFactory getInstance (){
        return instance;
    }


    public LibraryService getLibraryService (){
        return libraryService;
    }
}
