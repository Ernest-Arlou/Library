package by.jwd.library.service.util;


import by.jwd.library.bean.MediaPage;

import java.util.ArrayList;
import java.util.List;

public class Pagination {

    private static final int MAX_NAVIGATION_PAGES = 6;

    private static final Pagination instance = new Pagination();

    private Pagination() {
    }

    public static Pagination getInstance() {
        return instance;
    }


    public List<Integer> calculateNavigationPages(int page, int maxItemsPerPage, int totalItems) {
        return calculate(page, maxItemsPerPage, MAX_NAVIGATION_PAGES, totalItems);
    }

    public List<Integer> calculateNavigationPages(MediaPage mediaPage) {
        return calculate(mediaPage.getPage(), mediaPage.getItemsPerPage(), MAX_NAVIGATION_PAGES, mediaPage.getTotalItems());
    }

    private List<Integer> calculate(int page, int maxItemsPerPage, int maxNavigationPages, int totalItems) {
        int pageIndex = page - 1 < 0 ? 0 : page - 1;
        int currentPage = pageIndex + 1;

        int totalPages;
        if (totalItems % maxItemsPerPage == 0) {
            totalPages = totalItems / maxItemsPerPage;
        } else {
            totalPages = (totalItems / maxItemsPerPage) + 1;
        }


        if (maxNavigationPages > totalPages) {
            maxNavigationPages = totalPages;
        }


        List<Integer> navigationPages = new ArrayList<>();

        int current = currentPage > totalPages ? totalPages : currentPage;
        int begin = current - maxNavigationPages / 2;
        int end = current + maxNavigationPages / 2;

        // The first page
        navigationPages.add(1);
        if (begin > 2) {
            // Using for '...'
            navigationPages.add(-1);
        }

        for (int i = begin; i < end; i++) {
            if (i > 1 && i < totalPages) {
                navigationPages.add(i);
            }
        }

        if (end < totalPages - 2) {
            // Using for '...'
            navigationPages.add(-1);
        }
        // The last page.
        navigationPages.add(totalPages);
        return navigationPages;
    }


}
