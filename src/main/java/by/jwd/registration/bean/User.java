package by.jwd.registration.bean;

import java.io.Serializable;


public class User implements Serializable, Cloneable {
    private static final long serialVersionUID = 867171647832618785L;
    private String name;
    private String email;
    private String login;
    private String password;
    private boolean isAdmin;

    public User (){
        name = "Name";
        email = "Email";
        login = "login";
        password = "password";
        isAdmin = false;
    }

    public User (String name, String login, String password, boolean isAdmin){
        setName(name);
        setLogin(login);
        setPassword(password);
        setAdmin(isAdmin);
    }

    public User (User user){
        this.name = user.getName();
        this.email = user.getEmail();
        this.login = user.getLogin();
        this.password = user.getPassword();
        this.isAdmin = user.isAdmin();
    }

    public String getEmail (){
        return email;
    }

    public void setEmail (String email){
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

    final public boolean isAdmin (){
        return isAdmin;
    }

    final public void setAdmin (boolean admin){
        isAdmin = admin;
    }

    @Override
    public boolean equals (Object o){
        if (o == null) return false;
        if (this == o) return true;
        if ((o.getClass() != this.getClass())) return false;
        User user = (User) o;
        return name.equals(user.name) &&
                login.equals(user.login) &&
                password.equals(user.password);
    }

    @Override
    public int hashCode (){
        return name.hashCode() + login.hashCode() + password.hashCode();
    }

    @Override
    public String toString (){
        return getClass().getName() + "@" + Integer.toHexString(hashCode()) +
                "User{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", isAdmin=" + isAdmin +
                '}';
    }

    @Override
    public User clone () throws CloneNotSupportedException{
        return new User(this);
    }
}
