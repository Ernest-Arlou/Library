package by.jwd.registration.dao.connectionpool;


import java.util.ResourceBundle;

public class DBResourceManager {
    private final static DBResourceManager instance = new DBResourceManager();
    ResourceBundle resourceBundle = ResourceBundle.getBundle(
            "by.jwd.registration.dao.connectionpool.Resource");

    public static DBResourceManager getInstance (){
        return instance;
    }

    public String getValue (String key){
        return resourceBundle.getString(key);
    }
}