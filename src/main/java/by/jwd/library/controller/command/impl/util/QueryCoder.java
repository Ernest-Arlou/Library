package by.jwd.library.controller.command.impl.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class QueryCoder {
    private static final String SLASH_PATTERN = "/";
    private static final String EQUALS_PATTERN = "=";
    private static final String QUESTION_PATTERN = "\\?";
    private static final String AND_PATTERN = "&";

    private static final String SLASH_CODE = "%2f";
    private static final String EQUALS_CODE = "%3d";
    private static final String QUESTION_CODE = "%3f";
    private static final String AND_CODE = "%26";

    public static String code(String string){

        Pattern patternSlash = Pattern.compile(SLASH_PATTERN);
        Pattern patternEquals = Pattern.compile(EQUALS_PATTERN);
        Pattern patternQuestion = Pattern.compile(QUESTION_PATTERN);
        Pattern patternAnd = Pattern.compile(AND_PATTERN);


        Matcher matcher = patternSlash.matcher(string);
        string = matcher.replaceAll(SLASH_CODE);

        matcher = patternEquals.matcher(string);
        string = matcher.replaceAll(EQUALS_CODE);

        matcher = patternQuestion.matcher(string);
        string = matcher.replaceAll(QUESTION_CODE);

        matcher = patternAnd.matcher(string);
        string = matcher.replaceAll(AND_CODE);

        return string;
    }


}
