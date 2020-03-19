package by.jwd.registration.bean;

import java.util.Objects;

public class RegisterBean {

    private String firstname;
    private String lastname;
    private String username;
    private String password;

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
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
        RegisterBean that = (RegisterBean) o;
        return Objects.equals(firstname, that.firstname) &&
                Objects.equals(lastname, that.lastname) &&
                Objects.equals(username, that.username) &&
                Objects.equals(password, that.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstname, lastname, username, password);
    }

    @Override
    public String toString() {
        return "RegisterBean{" +
                "firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
