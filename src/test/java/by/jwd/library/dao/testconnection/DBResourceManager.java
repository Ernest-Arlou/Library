package by.jwd.library.dao.testconnection;


import java.util.ResourceBundle;

public class DBResourceManager {
    private static final String DB_PROPERTIES = "testdb";
    private final static DBResourceManager instance = new DBResourceManager();
    ResourceBundle resourceBundle = ResourceBundle.getBundle(DB_PROPERTIES);

    public static DBResourceManager getInstance() {
        return instance;
    }

    public String getValue(String key) {
        return resourceBundle.getString(key);
    }
}