package by.jwd.library.service.validation.factory;

import by.jwd.library.service.validation.LibraryValidator;
import by.jwd.library.service.validation.UserValidator;
import by.jwd.library.service.validation.impl.LibraryValidatorImpl;
import by.jwd.library.service.validation.impl.UserValidatorImpl;

public final class ValidationFactory {
    private static final ValidationFactory instance = new ValidationFactory();

    private final UserValidator userValidator = new UserValidatorImpl();
    private final LibraryValidator libraryValidator = new LibraryValidatorImpl();

    private ValidationFactory() {
    }

    public static ValidationFactory getInstance() {
        return instance;
    }

    public UserValidator getUserValidator() {
        return userValidator;
    }

    public LibraryValidator getLibraryValidator() {
        return libraryValidator;
    }


}
