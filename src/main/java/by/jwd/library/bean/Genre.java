package by.jwd.library.bean;

import java.io.Serializable;
import java.util.Objects;

public class Genre implements Serializable {
    private static final long serialVersionUID = 8741635207694091933L;

    private int id;

    private String genre;

    public Genre() {
        setId(-1);
        setGenre("NoGenre");
    }

    public Genre(int id, String genre) {
        setId(id);
        setGenre(genre);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (this == obj) return true;
        if ((obj.getClass() != this.getClass())) return false;
        Genre genre1 = (Genre) obj;
        return id == genre1.id &&
                genre.equals(genre1.genre);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, genre);
    }

    @Override
    public String toString() {
        return getClass().getName() + "@" + Integer.toHexString(hashCode()) +
                "Genre{" +
                "id=" + id +
                ", genre='" + genre + '\'' +
                '}';
    }
}