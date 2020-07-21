package by.jwd.library.service.validation;

import by.jwd.library.bean.MediaDetail;

import java.util.Set;

public interface LibraryValidator {

    Set<String> validateMediaDetail(MediaDetail mediaDetail);

    boolean validateId(int id);

    boolean validatePage(int page);

    boolean validateItemsPerPage(int itemsPerPage);
}
