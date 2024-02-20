package com.tomctrlcoding.library.controllers;

import com.tomctrlcoding.library.model.Book;
import com.tomctrlcoding.library.model.Genre;
import com.tomctrlcoding.library.services.BookServiceInterface;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@Path("/books")
@Singleton
public class BookController {

    private static Logger logger = LoggerFactory.getLogger(BookController.class);

    @Inject
    BookServiceInterface bookService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Book> getAllBooks() {
        return bookService.getAllBooks();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Book findBookById(@PathParam("id") String id){
        return bookService.findBookById(id);
    }

    @GET
    @Path("/genre")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Book> findBooksByGenre(@QueryParam("genre") Genre genre){
        return bookService.findBooksByGenre(genre);
    }
    @GET
    @Path("/query")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Book> findBooksByGenre(@QueryParam("genre") Genre genre, @QueryParam("author") String author){
        logger.info("QueryParams = {} , {}", author, genre);
        return bookService.findBooksByQueryParams(author, genre);
    }

}
