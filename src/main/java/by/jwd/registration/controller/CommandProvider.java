package by.jwd.registration.controller;

import by.jwd.registration.controller.command.Command;
import by.jwd.registration.controller.command.CommandName;
import by.jwd.registration.controller.command.impl.LogIn;

import java.util.HashMap;
import java.util.Map;

final class CommandProvider {
    private final Map<CommandName, Command> repository = new HashMap<>();

    CommandProvider (){
        repository.put(CommandName.LOG_IN, new LogIn());
//        repository.put(CommandName.LOG_OUT, new LogOut());
//        repository.put(CommandName.REGISTER_USER, new RegisterUser());

//        repository.put(CommandName.WRONG_REQUEST, new WrongRequest());
    }

    Command getCommand (String name){
        CommandName commandName = null;
        Command command = null;

        try {
            commandName = CommandName.valueOf(name.toUpperCase());
            command = repository.get(commandName);
        } catch (IllegalArgumentException | NullPointerException e) {
            //write log
            command = repository.get(CommandName.WRONG_REQUEST);
        }
        return command;
    }
}