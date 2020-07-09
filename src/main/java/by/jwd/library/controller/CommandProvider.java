package by.jwd.library.controller;

import by.jwd.library.controller.command.Command;
import by.jwd.library.controller.command.CommandName;
import by.jwd.library.controller.command.impl.*;

import java.util.HashMap;
import java.util.Map;

final class CommandProvider {
    private final Map<CommandName, Command> repository = new HashMap<>();

    CommandProvider() {
        repository.put(CommandName.LOGIN, new LogIn());
        repository.put(CommandName.REGISTRATION, new Registration());
        repository.put(CommandName.LOG_OUT, new LogOut());
        repository.put(CommandName.CHANGE_LOCAL, new ChangeLocale());
        repository.put(CommandName.PAGE, new Page());
        repository.put(CommandName.MEDIA_DETAIL, new MediaDetail());
        repository.put(CommandName.INDEX, new Index());
        repository.put(CommandName.USER_VERIFICATION, new VerifyUserForm());
        repository.put(CommandName.VERIFY_USER, new VerifyUser());
        repository.put(CommandName.CHANGE_PASSPORT_ID, new ChangePassportId());
        repository.put(CommandName.PROFILE, new Profile());
        repository.put(CommandName.EDIT_USER_INFO_FORM, new EditUserInfoForm());
        repository.put(CommandName.EDIT_USER_INFO, new EditUserInfo());
        repository.put(CommandName.EDIT_USER_PASSWORD_FORM, new EditUserPasswordForm());
        repository.put(CommandName.EDIT_USER_PASSWORD, new EditUserPassword());
        repository.put(CommandName.RESERVE, new Reserve());
        repository.put(CommandName.DELIVERY, new Delivery());
        repository.put(CommandName.DELETE_RESERVATION, new DeleteReservation());
        repository.put(CommandName.LOAN, new Loan());
        repository.put(CommandName.REGISTRATION_FORM, new RegistrationForm());
        repository.put(CommandName.LOGIN_FORM, new LoginForm());
        repository.put(CommandName.ADD_MEDIA_FORM, new AddMediaForm());

        repository.put(CommandName.WRONG_REQUEST, new WrongRequest());
    }

    Command getCommand(String name) {
        CommandName commandName = null;
        Command command = null;

        try {
            if (name == null) {
                commandName = CommandName.INDEX;
            } else
                commandName = CommandName.valueOf(name.toUpperCase());
            command = repository.get(commandName);
        } catch (IllegalArgumentException | NullPointerException e) {
            //write log
            command = repository.get(CommandName.WRONG_REQUEST);
        }
        return command;
    }
}