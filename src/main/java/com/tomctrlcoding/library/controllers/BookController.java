package com.tomctrlcoding.library.controllers;

import com.tomctrlcoding.library.model.Book;
import com.tomctrlcoding.library.services.BookServiceInterface;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

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
}
