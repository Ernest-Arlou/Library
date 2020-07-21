package by.jwd.library.controller.constant;

public class CommandURL {
    public static final String CONTROLLER = "/Library/Controller";
    public static final String PROFILE = CONTROLLER + "?command=profile";
    public static final String MEDIA_DETAIL = CONTROLLER + "?command=media_detail";
    public static final String EDIT_USER_INFO_FORM = CONTROLLER + "?command=edit_user_info_form";
    public static final String USER_VERIFICATION = CONTROLLER + "?command=user_verification";
    public static final String EDIT_USER_PASSWORD_FORM = CONTROLLER + "?command=edit_user_password_form";
    public static final String DELIVERY = CONTROLLER + "?command=delivery";
    public static final String FIRST_PAGE = CONTROLLER + "?command=page&page=1";
    public static final String REGISTRATION_FORM = CONTROLLER + "?command=registration_form";
    public static final String LOGIN_FORM = CONTROLLER + "?command=login_form";
    public static final String EDIT_MEDIA_FORM = CONTROLLER + "?command=edit_media_form";

    private CommandURL() {

    }
}
