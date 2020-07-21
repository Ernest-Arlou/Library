package by.jwd.library.bean;

import java.io.Serializable;
import java.util.Objects;


public class User implements Serializable {
    private static final long serialVersionUID = 867171647832618785L;
    private int userId;
    private String name;
    private String email;
    private String login;
    private String password;
    private String role;
    private String passportId;
    private String status;


    public User() {
        userId = 1;
        name = "NoName";
        email = "NoEmail";
        login = "NoLogin";
        password = "NoPassword";
        role = "NoRole";
        passportId = "00000000000000";
        status = "NoStatus";

    }

    public User(int userId, String name, String email, String login, String password, String passportId, String role, String status) {

        this.userId = userId;
        this.name = name;
        this.email = email;
        this.login = login;
        this.password = password;
        this.role = role;
        this.passportId = passportId;
        this.status = status;
    }


    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getPassportId() {
        return passportId;
    }

    public void setPassportId(String passportId) {
        this.passportId = passportId;
    }

    final public String getEmail() {
        return email;
    }

    final public void setEmail(String email) {
        this.email = email;
    }

    final public String getName() {
        return name;
    }

    final public void setName(String name) {
        this.name = name;
    }

    final public String getLogin() {
        return login;
    }

    final public void setLogin(String login) {
        this.login = login;
    }

    final public String getPassword() {
        return password;
    }

    final public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (this == obj) return true;
        if ((obj.getClass() != this.getClass())) return false;
        User user = (User) obj;
        return Objects.equals(name, user.name) &&
                Objects.equals(email, user.email) &&
                Objects.equals(login, user.login) &&
                Objects.equals(password, user.password) &&
                Objects.equals(role, user.role) &&
                Objects.equals(passportId, user.passportId) &&
                Objects.equals(status, user.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, email, login, password, role, passportId, status);
    }

    @Override
    public String toString() {
        return getClass().getName() + "@" + Integer.toHexString(hashCode()) +
                "User{" +
                "userId=" + userId +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", role='" + role + '\'' +
                ", passportId='" + passportId + '\'' +
                ", status='" + status + '\'' +
                '}';
    }

}
