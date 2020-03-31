package by.jwd.registration.dao.connectionpool;

import java.util.ListResourceBundle;

public class Resource extends ListResourceBundle {
    private static final Object[][] contents = {
            {"db.driver", "com.mysql.jdbc.Driver"},
            {"db.url", "jdbc:mysql://localhost:3306/library?useUnicode=true&serverTimezone=UTC&useSSL=false"},
            {"db.user", "root"},
            {"db.password", "1234"},
            {"db.poolsize", "5"}
    };

    @Override
    protected Object[][] getContents (){
        return contents;
    }
}
