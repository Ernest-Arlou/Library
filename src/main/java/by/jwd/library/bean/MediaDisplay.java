package by.jwd.library.bean;

import java.io.Serializable;
import java.util.Objects;

public class MediaDisplay implements Serializable {
    private static final long serialVersionUID = -7015771668470127450L;
    private int mediaID;
    private String picture;
    private String materialType;
    private String title;
    private String summary;
    private String publisher;
    private String language;

    public MediaDisplay() {
        setMediaID(-1);
        setPicture("NoPicture");
        setMaterialType("NoType");
        setTitle("NoTitle");
        setSummary("NoSummary");
        setPublisher("NoPublisher");
        setLanguage("NoLanguage");
    }

    public MediaDisplay(int mediaID, String picture, String materialType, String title, String summary, String publisher, String language) {
        setMediaID(mediaID);
        setPicture(picture);
        setMaterialType(materialType);
        setTitle(title);
        setSummary(summary);
        setPublisher(publisher);
        setLanguage(language);
    }

    public int getMediaID() {
        return mediaID;
    }

    public void setMediaID(int mediaID) {
        this.mediaID = mediaID;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getMaterialType() {
        return materialType;
    }

    public void setMaterialType(String materialType) {
        this.materialType = materialType;
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

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (this == obj) return true;
        if ((obj.getClass() != this.getClass())) return false;
        MediaDisplay that = (MediaDisplay) obj;
        return mediaID == that.mediaID &&
                picture.equals(that.picture) &&
                materialType.equals(that.materialType) &&
                title.equals(that.title) &&
                summary.equals(that.summary) &&
                publisher.equals(that.publisher) &&
                language.equals(that.language);
    }

    @Override
    public int hashCode() {
        return Objects.hash(mediaID, picture, materialType, title, summary, publisher, language);
    }

    @Override
    public String toString() {
        return getClass().getName() + "@" + Integer.toHexString(hashCode()) +
                "MediaDisplay{" +
                "mediaTypeID=" + mediaID +
                ", picture='" + picture + '\'' +
                ", materialType='" + materialType + '\'' +
                ", title='" + title + '\'' +
                ", summary='" + summary + '\'' +
                ", publisher='" + publisher + '\'' +
                ", language='" + language + '\'' +
                '}';
    }
}
