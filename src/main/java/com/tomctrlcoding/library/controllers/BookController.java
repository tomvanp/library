package com.tomctrlcoding.library.controllers;

import com.tomctrlcoding.library.model.Book;
import com.tomctrlcoding.library.model.Genre;
import com.tomctrlcoding.library.services.BookServiceInterface;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@Path("/books")
@Singleton
public class BookController {

    private final Logger logger = LoggerFactory.getLogger(BookController.class);

    @Inject
    BookServiceInterface bookService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllBooks() {
        List<Book> allBooks = bookService.getAllBooks();
        return Response.status(Response.Status.OK).entity(allBooks).build();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response findBookById(@PathParam("id") ObjectId id){
        Book book = bookService.findBookById(id);
        return Response.status(Response.Status.OK).entity(book).build();
    }

    @GET
    @Path("/genre")
    @Produces(MediaType.APPLICATION_JSON)
    public Response findBooksByGenre(@QueryParam("genre") Genre genre){
        List<Book> allBooks = bookService.findBooksByGenre(genre);
        return Response.status(Response.Status.OK).entity(allBooks).build();
    }
    @GET
    @Path("/query")
    @Produces(MediaType.APPLICATION_JSON)
    public Response findBooksByGenre(@QueryParam("genre") Genre genre, @QueryParam("author") String author){
        logger.info("QueryParams = {} , {}", author, genre);
        List<Book> allBooks = bookService.findBooksByQueryParams(author, genre);
        return Response.status(Response.Status.OK).entity(allBooks).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response insertNewBook(Book book) {
        var newBook = bookService.insertNewBook(book);
        return Response.status(Response.Status.CREATED).entity(newBook).build();
    }

    @DELETE
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response deleteBookById(@PathParam("id") ObjectId id) {
         bookService.deleteBook(id);
         return Response.status(Response.Status.OK).build();
    }

}
