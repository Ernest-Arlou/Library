package by.jwd.library.bean;

import java.io.Serializable;
import java.util.Objects;


public class User implements Serializable, Cloneable {
    private static final long serialVersionUID = 867171647832618785L;
    private String name;
    private String email;
    private String login;
    private String password;
    private String role;
    private String passportId;

    public User (){
        name = "Name";
        email = "Email";
        login = "login";
        password = "password";
        role = "user";
        passportId = "000000000000";

    }

    public User (String name, String email, String login, String password, String passportId, String role){
        setName(name);
        setEmail(email);
        setLogin(login);
        setPassword(password);
        setRole(role);
        setPassportId(passportId);
    }

    public User (User user){
        this.name = user.getName();
        this.email = user.getEmail();
        this.login = user.getLogin();
        this.password = user.getPassword();
        this.role = user.getRole();
        this.passportId= user.getPassportId();
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

    final public String getEmail (){
        return email;
    }

    final public void setEmail (String email){
        this.email = email;
    }

    final public String getName (){
        return name;
    }

    final public void setName (String name){
        this.name = name;
    }

    final public String getLogin (){
        return login;
    }

    final public void setLogin (String login){
        this.login = login;
    }

    final public String getPassword (){
        return password;
    }

    final public void setPassword (String password){
        this.password = password;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (this == obj) return true;
        if ((obj.getClass() != this.getClass())) return false;
        User user = (User) obj;
        return name.equals(user.name) &&
                email.equals(user.email) &&
                login.equals(user.login) &&
                password.equals(user.password) &&
                role.equals(user.role);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, email, login, password, role);
    }

    @Override
    public String toString() {
        return getClass().getName() + "@" + Integer.toHexString(hashCode()) +
                "User{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", role='" + role + '\'' +
                ", passportId='" + passportId + '\'' +
                '}';
    }

    @Override
    public User clone () throws CloneNotSupportedException{
        return new User(this);
    }
}
