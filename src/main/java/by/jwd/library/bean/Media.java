package by.jwd.library.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Media implements Serializable, Cloneable {
    private static final long serialVersionUID = -807197393787042287L;

    private int id;
    private String title;
    private String picture;
    private String summary;
    private String series;

    private List <Author> authors;
    private List <Genre> genres;


    public Media(){
        setId(-1);
        setTitle("NoTitle");
        setPicture("NoPicture");
        setSummary("NoSummary");
        setSeries("NoSeries");

        authors = new ArrayList<>();
        genres = new ArrayList<>();
    }

    public Media(int id, String title, String picture, String summary, String series, List authors, List genres){
        setId(id);
        setTitle(title);
        setPicture(picture);
        setSummary(summary);
        setSeries(series);
        setAuthors(authors);
        setGenres(genres);
    }

    public void addAuthor(Author author){
        authors.add(author);
    }

    public void addGenre(Genre genre){
        genres.add(genre);
    }
    public void deleteAuthor(Author author){
        authors.remove(author);
    }

    public void deleteGenre(Genre genre){
        genres.remove(genre);
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getSeries() {
        return series;
    }

    public void setSeries(String series) {
        this.series = series;
    }

    public List<Author> getAuthors() {
        return authors;
    }

    public void setAuthors(List<Author> authors) {
        this.authors = authors;
    }

    public List<Genre> getGenres() {
        return genres;
    }

    public void setGenres(List<Genre> genres) {
        this.genres = genres;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Media media = (Media) obj;
        return id == media.id &&
                title.equals(media.title) &&
                picture.equals(media.picture) &&
                series.equals(media.series) &&
                authors.equals(media.authors) &&
                genres.equals(media.genres);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, picture, series, authors, genres);
    }

    @Override
    public String toString() {
        return getClass().getName() + "@" + Integer.toHexString(hashCode()) +
                "Media{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", picture='" + picture + '\'' +
                ", summary='" + summary + '\'' +
                ", series='" + series + '\'' +
                ", authors=" + authors +
                ", genres=" + genres +
                '}'+"\n";
    }
}
