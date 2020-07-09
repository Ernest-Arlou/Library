package by.jwd.library.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MediaDetail implements Serializable {
    private static final long serialVersionUID = 7759631852833962220L;

    private int mediaID;

    private int totalCopies;
    private int availableCopies;
    private int reservedCopies;
    private int loanedCopies;

    private double price;

    private String title;
    private String summary;
    private String iSBN;
    private String picture;
    private String publisher;
    private String materialType;
    private String language;
    private String restriction;

    private List<Author> authors;
    private List<Genre> genres;

    public MediaDetail() {
        setMediaID(-1);

        setTotalCopies(-1);
        setAvailableCopies(-1);
        setReservedCopies(-1);
        setLoanedCopies(-1);

        setPrice(-1);

        setTitle("NoTitle");
        setSummary("NoSummary");
        setiSBN("NoISBN");
        setPicture("NoPicture");
        setPublisher("NoPublisher");
        setMaterialType("NoMaterialType");
        setLanguage("NoLanguage");
        setRestriction("NoRestriction");


        authors = new ArrayList<>();
        genres = new ArrayList<>();
    }

    public MediaDetail(int mediaID,
                       int totalCopies, int availableCopies, int reservedCopies, int loanedCopies,
                       double price,
                       String title, String summary, String iSBN, String picture,
                       String publisher, String materialType, String language, String restriction,
                       List<Author> authors, List<Genre> genres) {
        setMediaID(mediaID);

        setTotalCopies(totalCopies);
        setAvailableCopies(availableCopies);
        setReservedCopies(reservedCopies);
        setLoanedCopies(loanedCopies);

        setPrice(price);

        setTitle(title);
        setSummary(summary);
        setiSBN(iSBN);
        setPicture(picture);
        setPublisher(publisher);
        setMaterialType(materialType);
        setLanguage(language);
        setRestriction(restriction);

        setAuthors(authors);
        setGenres(genres);
    }

    public String getRestriction() {
        return restriction;
    }

    public void setRestriction(String restriction) {
        this.restriction = restriction;
    }

    public int getTotalCopies() {
        return totalCopies;
    }

    public void setTotalCopies(int totalCopies) {
        this.totalCopies = totalCopies;
    }

    public int getAvailableCopies() {
        return availableCopies;
    }

    public void setAvailableCopies(int availableCopies) {
        this.availableCopies = availableCopies;
    }

    public int getReservedCopies() {
        return reservedCopies;
    }

    public void setReservedCopies(int reservedCopies) {
        this.reservedCopies = reservedCopies;
    }

    public int getLoanedCopies() {
        return loanedCopies;
    }

    public void setLoanedCopies(int loanedCopies) {
        this.loanedCopies = loanedCopies;
    }

    public String getiSBN() {
        return iSBN;
    }

    public void setiSBN(String iSBN) {
        this.iSBN = iSBN;
    }

    public int getMediaID() {
        return mediaID;
    }

    public void setMediaID(int mediaID) {
        this.mediaID = mediaID;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getMaterialType() {
        return materialType;
    }

    public void setMaterialType(String materialType) {
        this.materialType = materialType;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
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
        if (obj == null) return false;
        if (this == obj) return true;
        if ((obj.getClass() != this.getClass())) return false;
        MediaDetail that = (MediaDetail) obj;
        return mediaID == that.mediaID &&
                totalCopies == that.totalCopies &&
                availableCopies == that.availableCopies &&
                reservedCopies == that.reservedCopies &&
                loanedCopies == that.loanedCopies &&
                Double.compare(that.price, price) == 0 &&
                title.equals(that.title) &&
                summary.equals(that.summary) &&
                Objects.equals(iSBN, that.iSBN) &&
                Objects.equals(picture, that.picture) &&
                Objects.equals(publisher, that.publisher) &&
                Objects.equals(materialType, that.materialType) &&
                Objects.equals(language, that.language) &&
                Objects.equals(restriction, that.restriction) &&
                Objects.equals(authors, that.authors) &&
                Objects.equals(genres, that.genres);
    }

    @Override
    public int hashCode() {
        return Objects.hash(mediaID, totalCopies, availableCopies, reservedCopies, loanedCopies, price, title, summary, iSBN, picture, publisher, materialType, language, restriction, authors, genres);
    }

    @Override
    public String toString() {
        return getClass().getName() + "@" + Integer.toHexString(hashCode()) +
                "MediaDetail{" +
                "mediaID=" + mediaID +
                ", totalCopies=" + totalCopies +
                ", availableCopies=" + availableCopies +
                ", reservedCopies=" + reservedCopies +
                ", loanedCopies=" + loanedCopies +
                ", price=" + price +
                ", title='" + title + '\'' +
                ", summary='" + summary + '\'' +
                ", iSBN='" + iSBN + '\'' +
                ", picture='" + picture + '\'' +
                ", publisher='" + publisher + '\'' +
                ", materialType='" + materialType + '\'' +
                ", language='" + language + '\'' +
                ", restriction='" + restriction + '\'' +
                ", authors=" + authors +
                ", genres=" + genres +
                '}';
    }
}
