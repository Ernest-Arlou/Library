package by.jwd.library.controller.command.impl;

import by.jwd.library.bean.Author;
import by.jwd.library.bean.Genre;
import by.jwd.library.controller.command.Command;
import by.jwd.library.controller.command.CommandException;
import by.jwd.library.controller.command.impl.util.LocalMessageCoder;
import by.jwd.library.controller.constants.CommandURL;
import by.jwd.library.controller.constants.RequestAttribute;
import by.jwd.library.controller.constants.RequestParameter;
import by.jwd.library.bean.MediaDetail;
import by.jwd.library.controller.constants.local.LocalParameter;
import by.jwd.library.service.ServiceException;
import by.jwd.library.service.factory.ServiceFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AddMedia implements Command {
    private static final String DELIMITER = ";";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {

        String title = request.getParameter(RequestParameter.TITLE);
        String iSBN = request.getParameter(RequestParameter.ISBN);

        String publisher = request.getParameter(RequestParameter.PUBLISHER);
        String format = request.getParameter(RequestParameter.FORMAT);
        String language = request.getParameter(RequestParameter.LANGUAGE);
        double price = Double.parseDouble(request.getParameter(RequestParameter.PRICE));
        String authors = request.getParameter(RequestParameter.AUTHORS);
        String genres = request.getParameter(RequestParameter.GENRES);
        int totalCopies = Integer.parseInt(request.getParameter(RequestParameter.COPIES));
        String summary = request.getParameter(RequestParameter.SUMMARY);
        String picture = request.getParameter(RequestParameter.PICTURE);
        String restriction = request.getParameter(RequestParameter.RESTRICTION);

        MediaDetail mediaDetail = new MediaDetail();

        mediaDetail.setTitle(title);
        mediaDetail.setiSBN(iSBN);
        mediaDetail.setPublisher(publisher);
        mediaDetail.setFormat(format);
        mediaDetail.setLanguage(language);
        mediaDetail.setPrice(price);
        mediaDetail.setTotalCopies(totalCopies);
        mediaDetail.setSummary(summary);
        mediaDetail.setPicture(picture);
        mediaDetail.setRestriction(restriction);

        List<Author> authorList = new ArrayList<>();

        String [] authorsMas = authors.split(DELIMITER);
        for (String str :
                authorsMas) {
            authorList.add(new Author(1, str));
        }
        mediaDetail.setAuthors(authorList);

        List<Genre> genreList = new ArrayList<>();

        String [] genresMas = genres.split(DELIMITER);
        for (String str :
                genresMas) {
            genreList.add(new Genre(1, str));
        }
        mediaDetail.setGenres(genreList);

        try {
            int mediaId = ServiceFactory.getInstance().getLibraryService().addMedia(mediaDetail);
            response.sendRedirect(CommandURL.MEDIA_DETAIL + "&" + RequestParameter.MEDIA_ID
                    + "=" +  mediaId);
        } catch (ServiceException | IOException e) {
            throw new CommandException(e);
        }
    }
}
