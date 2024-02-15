package com.tomctrlcoding.library.controllers;

import com.tomctrlcoding.library.model.Book;
import com.tomctrlcoding.library.model.Genre;
import com.tomctrlcoding.library.services.BookServiceInterface;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;

import java.util.List;

@Path("/books")
@Singleton
public class BookController {

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
    public List<Book> findBooksByGenre(@QueryParam("genre") String genre, @QueryParam("author") String author){
        return bookService.findBooksByQueryParams(author, genre);
    }

}
