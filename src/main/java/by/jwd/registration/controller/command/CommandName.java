package by.jwd.registration.controller.command;

public enum CommandName {
    REGISTER_USER,
    REGISTER_ADMIN,
    LOG_IN,
    LOG_OUT,
    EDIT_NAME,
    EDIT_LOGIN,
    EDIT_PASSWORD,
    GET_LOANS,
    GET_ALL_LOANS,
    GET_ALL_USERS,
    GET_USER_LOANS,

    ADD_BOOK,
    REMOVE_BOOK,
    EDIT_AUTHOR,
    EDIT_ISBN,
    EDIT_SUBJECT,
    EDIT_TITLE,

    GET_ALL_BOOKS,
    SEARCH_BOOKS,
    LOAN_BOOK,
    RETURN_BOOK,

    WRONG_REQUEST
}