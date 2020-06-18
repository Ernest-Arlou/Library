package by.jwd.library.controller;

import by.jwd.library.controller.command.Command;
import by.jwd.library.controller.command.CommandName;
import by.jwd.library.controller.command.impl.*;

import java.util.HashMap;
import java.util.Map;

final class CommandProvider {
    private final Map<CommandName, Command> repository = new HashMap<>();

    CommandProvider (){
        repository.put(CommandName.LOGIN, new LogIn());
//        repository.put(CommandName.REGISTRATION, new Registration());
        repository.put(CommandName.LOG_OUT, new LogOut());
        repository.put(CommandName.USER_INFO, new UserInfo());
        repository.put(CommandName.SWITCH_LOCALE, new SwitchLocale());
        repository.put(CommandName.PAGE, new Page());
        repository.put(CommandName.MEDIA_DETAIL, new MediaDetail());
        repository.put(CommandName.INDEX, new Index());
        repository.put(CommandName.USER_REGISTRATION, new UserRegistration());
        repository.put(CommandName.USER_REGISTRATION_PAGE, new UserRegistrationPage());


        repository.put(CommandName.WRONG_REQUEST, new WrongRequest());
    }

    Command getCommand (String name){
        CommandName commandName = null;
        Command command = null;

        try {
            if (name == null){
                commandName = CommandName.INDEX;
            }else
            commandName = CommandName.valueOf(name.toUpperCase());
            command = repository.get(commandName);
        } catch (IllegalArgumentException | NullPointerException e) {
            //write log
            command = repository.get(CommandName.WRONG_REQUEST);
        }
        return command;
    }
}