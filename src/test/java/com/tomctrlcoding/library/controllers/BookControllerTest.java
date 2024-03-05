package com.tomctrlcoding.library.controllers;

import com.tomctrlcoding.library.model.Book;
import com.tomctrlcoding.library.model.Genre;
import com.tomctrlcoding.library.services.BookServiceInterface;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Year;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BookControllerTest {

    @Mock
    private BookServiceInterface bookService;

    @InjectMocks
    private BookController bookController;


    @Test
    void getAllBooksTest(){
        var bookList = bookList();
        when(bookService.getAllBooks()).thenReturn(bookList);

        var books = bookController.getAllBooks();

        assertIterableEquals(books, bookList);

        verify(bookService).getAllBooks();
    }

    private List<Book> bookList(){
        Book book1 = new Book(new ObjectId(), "Test Title", "John Doe", Genre.MYSTERY, "Fake Publish", Year.of(2020), "0123456789", null);
        Book book2 = new Book(new ObjectId(), "Test Title 2", "Jane Doe", Genre.DRAMA, "Fake Publish", Year.of(2021), "0123456788", null);

        List<Book> books= new ArrayList<>();
        books.add(book1);
        books.add(book2);
        return books;
    }
}
