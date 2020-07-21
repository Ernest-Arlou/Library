package by.jwd.library.service.validation.impl;

import by.jwd.library.bean.MediaDetail;
import by.jwd.library.service.validation.InvalidParameter;
import by.jwd.library.service.validation.LibraryValidator;

import java.util.HashSet;
import java.util.Set;

public class LibraryValidatorImpl implements LibraryValidator {

    public LibraryValidatorImpl() {
    }

    @Override
    public Set<String> validateMediaDetail(MediaDetail mediaDetail) {

        Set<String> validationResult = new HashSet<>();

        if (mediaDetail.getMediaID() < 1) {
            validationResult.add(String.valueOf(InvalidParameter.MEDIA_ID));
        }

        if (mediaDetail.getTotalCopies() < 0) {
            validationResult.add(String.valueOf(InvalidParameter.TOTAL_COPIES));
        }

        if (mediaDetail.getPrice() < 0) {
            validationResult.add(String.valueOf(InvalidParameter.PRICE));
        }

        if (mediaDetail.getTitle() == null || mediaDetail.getTitle().isBlank()) {
            validationResult.add(String.valueOf(InvalidParameter.TITLE));
        }

        if (mediaDetail.getSummary() == null || mediaDetail.getSummary().isBlank()) {
            validationResult.add(String.valueOf(InvalidParameter.SUMMARY));
        }

        if (mediaDetail.getiSBN() == null || mediaDetail.getiSBN().isBlank()) {
            validationResult.add(String.valueOf(InvalidParameter.ISBN));
        }

        if (mediaDetail.getPicture() == null || mediaDetail.getPicture().isBlank()) {
            validationResult.add(String.valueOf(InvalidParameter.PICTURE));
        }

        if (mediaDetail.getPublisher() == null || mediaDetail.getPublisher().isBlank()) {
            validationResult.add(String.valueOf(InvalidParameter.PUBLISHER));
        }

        if (mediaDetail.getFormat() == null || mediaDetail.getFormat().isBlank()) {
            validationResult.add(String.valueOf(InvalidParameter.FORMAT));
        }

        if (mediaDetail.getLanguage() == null || mediaDetail.getLanguage().isBlank()) {
            validationResult.add(String.valueOf(InvalidParameter.LANGUAGE));
        }

        return validationResult;
    }

    @Override
    public boolean validateId(int id) {
        return id > 0;
    }

    @Override
    public boolean validatePage(int page) {
        return page > 0;
    }

    @Override
    public boolean validateItemsPerPage(int itemsPerPage) {
        return itemsPerPage > 0;
    }


}
