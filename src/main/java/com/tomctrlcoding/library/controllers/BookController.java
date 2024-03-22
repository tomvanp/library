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
    private BookServiceInterface bookService;

    /*
    *  private static final Supplier<WebApplicationException> NOT_FOUND =
         () -> new WebApplicationException(Response.Status.NOT_FOUND);
    */

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllBooks() {
        List<Book> allBooks = bookService.getAllBooks();
        return Response.ok().entity(allBooks).build();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response findBookById(@PathParam("id") ObjectId id){
        try {
            Book book = bookService.findBookById(id);
            return Response.ok().entity(book).build();
        } catch (NotFoundException ex) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

    }

    @GET
    @Path("/isbn/{isbn}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response findBookByISBN(@PathParam("isbn") String isbn){
        try {
            Book book = bookService.findBookByISBN(isbn);
            return Response.ok().entity(book).build();
        } catch (NotFoundException ex) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @GET
    @Path("/query")
    @Produces(MediaType.APPLICATION_JSON)
    public Response findBooksByQueryParam(@QueryParam("title") String title,
                                        @QueryParam("author") String author,
                                        @QueryParam("genre") Genre genre,
                                        @QueryParam("publisher") String publisher,
                                        @QueryParam("publishYear") String publishYear,
                                        @QueryParam("andCheck") boolean andCheck){

        List<Book> foundBooks = bookService.findBooksByQueryParams(title, author, genre, publisher, publishYear, andCheck);

        return Response.ok().entity(foundBooks).build();
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
         return Response.ok().build();
    }

}
