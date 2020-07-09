package by.jwd.library.bean;

import java.io.Serializable;
import java.util.Objects;

public class Author implements Serializable {
    private static final long serialVersionUID = -6727815541246145990L;

    private int id;

    private String fullName;


    public Author() {
        setId(-1);
        setFullName("NoName");

    }

    public Author(int id, String fullName) {
        setId(id);
        setFullName(fullName);

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }


    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (this == obj) return true;
        if ((obj.getClass() != this.getClass())) return false;
        Author author = (Author) obj;
        return id == author.id &&
                fullName.equals(author.fullName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, fullName);
    }

    @Override
    public String toString() {
        return getClass().getName() + "@" + Integer.toHexString(hashCode()) +
                "Author{" +
                "id=" + id +
                ", fullName='" + fullName + '\'' +
                '}';
    }
}