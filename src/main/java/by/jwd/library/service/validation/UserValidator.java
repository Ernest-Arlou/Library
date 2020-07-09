package by.jwd.library.service.validation;

import by.jwd.library.bean.User;

import java.util.Set;

public interface UserValidator {
    Set<String> validate(User user);

    boolean validateName(String name);

    boolean validateEmail(String email);

    boolean validateLogin(String login);

    boolean validatePassword(String password);

    boolean validatePassportId(String passportId);

    boolean validateStatus(String status);

    boolean validateRole(String role);

    boolean validateUserId(int userId);
}
