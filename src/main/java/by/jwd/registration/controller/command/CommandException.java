package by.jwd.registration.controller.command;

public class CommandException extends Exception {
    private static final long serialVersionUID = 7916620834287111893L;

    public CommandException (){
        super();
    }

    public CommandException (String message){
        super(message);
    }

    public CommandException (String message, Throwable cause){
        super(message, cause);
    }

    public CommandException (Throwable cause){
        super(cause);
    }

    protected CommandException (String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace){
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
