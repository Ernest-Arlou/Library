package by.jwd.library.bean;

import java.io.Serializable;
import java.util.Objects;

public class Author implements Serializable {
    private static final long serialVersionUID = -6727815541246145990L;

    private int id;

    private String fullName;
    private String penName;

    public Author(){
        setId(-1);
        setFullName("NoName");
        setPenName("NoPenName");
    }

    public Author(int id, String fullName, String penName){
        setId(id);
        setFullName(fullName);
        setPenName(penName);
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPenName() {
        return penName;
    }

    public void setPenName(String penName) {
        this.penName = penName;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (this == obj) return true;
        if ((obj.getClass() != this.getClass())) return false;
        Author author = (Author) obj;
        return id == author.id &&
                fullName.equals(author.fullName) &&
                penName.equals(author.penName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, fullName, penName);
    }

    @Override
    public String toString() {
        return getClass().getName() + "@" + Integer.toHexString(hashCode()) +
                "Author{" +
                "id=" + id +
                ", fullName='" + fullName + '\'' +
                ", penName='" + penName + '\'' +
                '}';
    }
}
