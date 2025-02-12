package com.tomctrlcoding.library.controllers;

import com.tomctrlcoding.library.model.Book;
import com.tomctrlcoding.library.model.Genre;
import com.tomctrlcoding.library.services.BookService;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.core.Response;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Year;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BookControllerTest {

    @Mock
    private BookService bookService;

    @InjectMocks
    private BookController bookController;


    @Test
    void testGetAllBooks(){
        var bookList = bookList();
        when(bookService.getAllBooks()).thenReturn(bookList);

        var response = bookController.getAllBooks();

        assertEquals(bookList, response.getEntity());

        verify(bookService).getAllBooks();
    }

    @Test
    void testGetBookById() {
        var id = new ObjectId();
        Book book = createTestBook(id);
        when(bookService.findBookById(id)).thenReturn(book);

        try (var response = bookController.findBookById(id)) {

            assertEquals(book, response.getEntity());
        }

        verify(bookService).findBookById(id);
    }

    @Test
    void testGetBookByIDNotFound() {
        when(bookService.findBookById(any(ObjectId.class))).thenThrow(NotFoundException.class);

        try (var response = bookController.findBookById(new ObjectId())) {
            assertEquals(Response.Status.NOT_FOUND, response.getStatusInfo());
        }

        verify(bookService, times(1)).findBookById(any(ObjectId.class));
    }

    @Test
    void testGetBookByISBN() {
        var id = new ObjectId();
        Book book = createTestBook(id);
        when(bookService.findBookByISBN(anyString())).thenReturn(book);

        try (var response = bookController.findBookByISBN("978-0-5521-3325-8")) {

            assertEquals(book, response.getEntity());
        }

        verify(bookService, times(1)).findBookByISBN(anyString());
    }

    @Test
    void testGetBookByISBNNotFound() {
        when(bookService.findBookByISBN(anyString())).thenThrow(NotFoundException.class);

        try (var response = bookController.findBookByISBN("978-0-5521-3325-8")) {
            assertEquals(Response.Status.NOT_FOUND, response.getStatusInfo());
        }

        verify(bookService, times(1)).findBookByISBN(anyString());
    }

    @Test
    void testQueryBooksByParams() {
        var title = "Test Title";
        var author = "John Doe";
        var genre = Genre.MYSTERY;
        var publisher = "Test Publish";
        var year = "2024";

        var books = bookList();

        when(bookService.findBooksByQueryParams(anyString(), anyString(), any(Genre.class), anyString(), anyString(), anyBoolean())).thenReturn(books);

        try (var response = bookController.findBooksByQueryParam(title, author, genre, publisher, year, false)) {
            assertEquals(books, response.getEntity());
        }

        verify(bookService).findBooksByQueryParams(anyString(), anyString(), any(Genre.class), anyString(), anyString(), anyBoolean());
    }

    @Test
    void testQueryBooksByParamsNoBooksFound() {
        var title = "Test Title";
        var author = "John Doe";
        var genre = Genre.MYSTERY;
        var publisher = "Test Publish";
        var year = "2024";

        List<Book> books = new ArrayList<>();

        when(bookService.findBooksByQueryParams(anyString(), anyString(), any(Genre.class), anyString(), anyString(), anyBoolean())).thenReturn(books);

        try (var response = bookController.findBooksByQueryParam(title, author, genre, publisher, year, false)) {
            assertEquals(0, ((ArrayList<Book>)response.getEntity()).size());
        }
        verify(bookService).findBooksByQueryParams(anyString(), anyString(), any(Genre.class), anyString(), anyString(), anyBoolean());
    }

    @Test
    void testInsertNewBook(){
        Book book = createTestBook(new ObjectId());
        when(bookService.insertNewBook(book)).thenReturn(book);

        try (var response = bookController.insertNewBook(book)) {

            assertEquals(book, response.getEntity());
        }

        verify(bookService).insertNewBook(book);
    }

    @Test
    void testDeleteBook(){
        var id = new ObjectId();

        doNothing().when(bookService).deleteBook(id);

        try (var response = bookController.deleteBookById(id)) {

            assertEquals(Response.Status.OK, response.getStatusInfo());
        }

        verify(bookService).deleteBook(id);
    }

    private Book createTestBook(ObjectId id) {
        var title = "Test Title";
        var author = "John Doe";
        var publisher = "Test Publish";
        var genre = Genre.MYSTERY;
        var year = "2024";
        var isbn = "978-0-5521-3325-8";

        return new Book(id, title, author, genre, publisher, Year.parse(year), isbn);
    }

    private List<Book> bookList(){
        Book book1 = new Book(new ObjectId(), "Test Title", "John Doe", Genre.MYSTERY, "Fake Publish", Year.of(2020), "0123456789");
        Book book2 = new Book(new ObjectId(), "Test Title 2", "Jane Doe", Genre.DRAMA, "Fake Publish", Year.of(2021), "0123456788");

        List<Book> books= new ArrayList<>();
        books.add(book1);
        books.add(book2);
        return books;
    }
}
