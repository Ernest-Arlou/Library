package by.jwd.registration.bean;

import java.util.Objects;

public class LoginInfo {
    private String username;
    private String password;

    public LoginInfo (){

    }

    public LoginInfo (String username, String password){
        setLogin(username);
        setPassword(password);
    }

    public String getUsername() {
        return username;
    }

    public void setLogin (String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LoginInfo login = (LoginInfo) o;
        return Objects.equals(username, login.username) &&
                Objects.equals(password, login.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, password);
    }

    @Override
    public String toString() {
        return "LoginBean{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
