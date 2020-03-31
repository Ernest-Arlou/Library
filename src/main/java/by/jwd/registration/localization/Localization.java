package by.jwd.registration.localization;

import java.util.ListResourceBundle;

public class Localization extends ListResourceBundle {
    private static final Object[][] contents = {
            {"local.locbutton.name.en", "EN"},
            {"local.locbutton.name.ru", "RU"},
            {"local.message", "Create property"},

    };

    @Override
    protected Object[][] getContents (){
        return contents;
    }
}