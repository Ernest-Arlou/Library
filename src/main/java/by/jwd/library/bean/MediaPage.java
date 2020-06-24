package by.jwd.library.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MediaPage implements Serializable {
    private static final long serialVersionUID = 6165757400781068605L;

    int page;
    int itemsPerPage;
    int totalItems;
    int totalPages;

    String search;

    List<MediaDisplay> mediaDisplay;
    List<Integer> navigationPages;

    public MediaPage() {
        setPage(-1);
        setItemsPerPage(-1);
        setTotalItems(-1);
        setTotalPages(-1);

        setSearch(null);

        mediaDisplay = new ArrayList<>();
        navigationPages = new ArrayList<>();
    }

    public MediaPage(int page, int itemsPerPage, int totalItems, int totalPages, List<MediaDisplay> mediaDisplay, List<Integer> navigationPages) {
        setPage(page);
        setItemsPerPage(itemsPerPage);
        setTotalItems(totalItems);
        setTotalPages(totalPages);
        setMediaDisplay(mediaDisplay);
        setNavigationPages(navigationPages);

    }

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getItemsPerPage() {
        return itemsPerPage;
    }

    public void setItemsPerPage(int itemsPerPage) {
        this.itemsPerPage = itemsPerPage;
    }

    public int getTotalItems() {
        return totalItems;
    }

    public void setTotalItems(int totalItems) {
        this.totalItems = totalItems;
    }

    public List<MediaDisplay> getMediaDisplay() {
        return mediaDisplay;
    }

    public void setMediaDisplay(List<MediaDisplay> mediaDisplay) {
        this.mediaDisplay = mediaDisplay;
    }

    public List<Integer> getNavigationPages() {
        return navigationPages;
    }

    public void setNavigationPages(List<Integer> navigationPages) {
        this.navigationPages = navigationPages;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (this == obj) return true;
        if ((obj.getClass() != this.getClass())) return false;
        MediaPage mediaPage = (MediaPage) obj;
        return page == mediaPage.page &&
                itemsPerPage == mediaPage.itemsPerPage &&
                totalItems == mediaPage.totalItems &&
                totalPages == mediaPage.totalPages &&
                mediaDisplay.equals(mediaPage.mediaDisplay) &&
                navigationPages.equals(mediaPage.navigationPages);
    }

    @Override
    public int hashCode() {
        return Objects.hash(page, itemsPerPage, totalItems, totalPages, mediaDisplay, navigationPages);
    }

    @Override
    public String toString() {
        return getClass().getName() + "@" + Integer.toHexString(hashCode()) +
                "MediaPage{" +
                "page=" + page +
                ", itemsPerPage=" + itemsPerPage +
                ", totalItems=" + totalItems +
                ", totalPages=" + totalPages +
                ", mediaDisplay=" + mediaDisplay +
                ", navigationPages=" + navigationPages +
                '}';
    }
}
