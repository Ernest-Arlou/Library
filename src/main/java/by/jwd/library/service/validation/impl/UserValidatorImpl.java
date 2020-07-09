package by.jwd.library.service.validation.impl;

import by.jwd.library.bean.User;
import by.jwd.library.service.validation.InvalidParameter;
import by.jwd.library.service.validation.UserValidator;
import by.jwd.library.util.UserRole;
import by.jwd.library.util.UserStatus;

import java.util.HashSet;
import java.util.Set;

public class UserValidatorImpl implements UserValidator {

    private static final String NAME_PATTERN = "^[a-z A-Zа-яА-Я]{4,20}$";
    private static final String EMAIL_PATTERN = "^\\w+([\\.-]?\\w+)*@\\w+([\\.-]?\\w+)*(\\.\\w{2,3})+$";
    private static final String LOGIN_PATTERN = "^[a-zA-Z0-9_-]{4,20}$";
    private static final String PASSWORD_PATTERN = "^[A-Za-z0-9]{4,20}$";
    private static final String PASSPORT_ID_PATTERN = "^(?!^0+$)[a-zA-Z0-9]{14,20}$";

    public UserValidatorImpl() {
    }

    @Override
    public Set<String> validate(User user) {

        Set<String> validationResult = new HashSet<>();

        if (user.getName() == null || !validateName(user.getName())) {
            validationResult.add(String.valueOf(InvalidParameter.NAME));
        }

        if (user.getEmail() == null || !validateEmail(user.getEmail())) {
            validationResult.add(String.valueOf(InvalidParameter.EMAIL));
        }

        if (user.getLogin() == null || !validateLogin(user.getLogin())) {
            validationResult.add(String.valueOf(InvalidParameter.LOGIN));
        }

        if (user.getPassword() == null || !validatePassword(user.getPassword())) {
            validationResult.add(String.valueOf(InvalidParameter.PASSWORD));
        }

        if (user.getPassportId() == null || !validatePassportId(user.getPassportId())) {
            validationResult.add(String.valueOf(InvalidParameter.PASSPORT_ID));
        }

        if (user.getRole() == null || !validateRole(user.getRole())) {
            validationResult.add(String.valueOf(InvalidParameter.ROLE));
        }

        if (user.getStatus() == null || !validateStatus(user.getStatus())) {
            validationResult.add(String.valueOf(InvalidParameter.STATUS));
        }

        if (!validateUserId(user.getUserId())) {
            validationResult.add(String.valueOf(InvalidParameter.USER_ID));
        }

        return validationResult;
    }

    @Override
    public boolean validateName(String name) {
        return name.matches(NAME_PATTERN);
    }

    @Override
    public boolean validateEmail(String email){
        return email.matches(EMAIL_PATTERN);
    }

    @Override
    public boolean validateLogin(String login) {
        return login.matches(LOGIN_PATTERN);
    }

    @Override
    public boolean validatePassword(String password) {
        return password.matches(PASSWORD_PATTERN);
    }

    @Override
    public boolean validatePassportId(String passportId) {
        return passportId.matches(PASSPORT_ID_PATTERN);
    }

    @Override
    public boolean validateStatus(String status) {
        return status.equals(UserStatus.ACTIVE) ||
                status.equals(UserStatus.UNVERIFIED);
    }

    @Override
    public boolean validateRole(String role) {
        return role.equals(UserRole.USER) ||
                role.equals(UserRole.ADMIN) ||
                role.equals(UserRole.LIBRARIAN);
    }

    @Override
    public boolean validateUserId(int userId) {
        return userId > 0;
    }


}
