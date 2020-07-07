package by.jwd.library.controller.command.impl.util;

import by.jwd.library.controller.constants.local.LocalParameter;

import java.nio.charset.StandardCharsets;
import java.util.Locale;
import java.util.ResourceBundle;

public class LocalMessageCoder {
    private static final String SPLIT_REG_EXP = "_";
    private static final String URL_CODE_PREFIX = "%";
    private static final String HEXADECIMAL_FORMAT = "%02x";

    public static String getCodedLocalizedMsg(String localeStr, String parameter) {

        ResourceBundle local = null;

        if (localeStr != null) {
            String[] parts = localeStr.split(SPLIT_REG_EXP);

            Locale locale = new Locale(parts[0], parts[1]);

            local = ResourceBundle.getBundle(LocalParameter.BUNDLE_PATH, locale);

        } else {
            local = ResourceBundle.getBundle(LocalParameter.BUNDLE_PATH);
        }

        String localizedStr = local.getString(parameter);
        byte[] bytes = localizedStr.getBytes(StandardCharsets.UTF_8);
        StringBuilder codedStr = new StringBuilder();
        for (byte b : bytes) {
            codedStr.append(URL_CODE_PREFIX);
            codedStr.append(String.format(HEXADECIMAL_FORMAT, b));
        }
        return String.valueOf(codedStr);
    }
    private LocalMessageCoder(){}
}
