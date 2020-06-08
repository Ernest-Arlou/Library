package by.jwd.library.bean;

import java.io.Serializable;
import java.util.Objects;

public class MediaType implements Serializable {
    private static final long serialVersionUID = 4588962202954024467L;
    private int id;
    private int mediaId;

    private double price;

    private String iSBN;
    private String materialType;
    private String publisher;
    private String language;

    public MediaType(){
        id=-1;
        mediaId= -1;
        setPrice(0.);
        setiSBN("000-0000000000");
        setMaterialType("noType");
        setPublisher("noPublisher");
        setLanguage("noLanguage");
    }

    public MediaType(int id, int mediaId, double price, String iSBN, String materialType, String publisher, String language){
        setId(id);
        setMediaId(mediaId);
        setPrice(price);
        setiSBN(iSBN);
        setMaterialType(materialType);
        setPublisher(publisher);
        setLanguage(language);
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public int getMediaId() {
        return mediaId;
    }

    public void setMediaId(int mediaId) {
        this.mediaId = mediaId;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getiSBN() {
        return iSBN;
    }

    public void setiSBN(String iSBN) {
        this.iSBN = iSBN;
    }

    public String getMaterialType() {
        return materialType;
    }

    public void setMaterialType(String materialType) {
        this.materialType = materialType;
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

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (this == obj) return true;
        if ((obj.getClass() != this.getClass())) return false;
        MediaType mediaType = (MediaType) obj;
        return id == mediaType.id &&
                mediaId == mediaType.mediaId &&
                Double.compare(mediaType.price, price) == 0 &&
                iSBN.equals(mediaType.iSBN) &&
                materialType.equals(mediaType.materialType) &&
                publisher.equals(mediaType.publisher) &&
                language.equals(mediaType.language);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, mediaId, price, iSBN, materialType, publisher, language);
    }

    @Override
    public String toString() {
        return getClass().getName() + "@" + Integer.toHexString(hashCode()) +
                "MediaType{" +
                "id=" + id +
                ", mediaId=" + mediaId +
                ", price=" + price +
                ", iSBN='" + iSBN + '\'' +
                ", materialType='" + materialType + '\'' +
                ", publisher='" + publisher + '\'' +
                ", language='" + language + '\'' +
                '}';
    }
}
