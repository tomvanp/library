package com.tomctrlcoding.library.services;

import com.tomctrlcoding.library.model.Book;
import com.tomctrlcoding.library.model.Genre;
import com.tomctrlcoding.library.repositories.BookRepositoryI;
import jakarta.inject.Inject;
import jakarta.nosql.document.DocumentTemplate;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Year;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BookServiceTest {

    @Mock
    private BookRepositoryI bookRepo;

    @Mock
    private DocumentTemplate template;

    @InjectMocks
    private BookService bookService;

    @Test
    void testCriteriaDocumentTemplate() {


    }

    @Test
     void testGetAllBooks(){
        List<Book> books = bookList();
        when(bookRepo.findAll()).thenReturn(books.stream());

        var result = bookService.getAllBooks();

        assertIterableEquals(result, books);

        verify(bookRepo).findAll();
    }

    @Test
    void testFindBookByID(){
        var id = new ObjectId();
        Book book = new Book(id, "Test Title", "John Doe", Genre.MYSTERY, "Fake Publish", Year.of(2020), "0123456789", null);

        when(bookRepo.findById(id)).thenReturn(Optional.of(book));

        var result = bookService.findBookById(id);

        assertEquals(result, book);

        verify(bookRepo).findById(id);
    }

    @Test
    void testInsertBook(){
        Book book = new Book(new ObjectId(), "Test Title", "John Doe", Genre.MYSTERY, "Fake Publish", Year.of(2020), "0123456789", null);

        when(template.insert(book)).thenReturn(book);

        var result = bookService.insertNewBook(book);

        assertEquals(result, book);

        verify(template).insert(book);
    }

    @Test
    void testQueryBooksAllNullParams() {
       // var result = bookService.findBooksByQueryParams(null, null, null, null, null, false);
    }

    @Test
    void testDeleteBook(){
        var id = new ObjectId();
        doNothing().when(template).delete(Book.class, id);

        bookService.deleteBook(id);

        verify(template).delete(Book.class, id);
    }

    private List<Book> bookList(){
        Book book1 = new Book(new ObjectId(), "Test Title 1", "John Doe", Genre.MYSTERY, "Fake Publish", Year.of(2020), "0123456789", null);
        Book book2 = new Book(new ObjectId(), "Test Title 2", "Jane Doe", Genre.DRAMA, "Fake Publish", Year.of(2021), "0123456788", null);
        Book book3 = new Book(new ObjectId(), "Test Title 3", "John Doe", Genre.MYSTERY, "Fake Publish", Year.of(2020), "0123456789", null);
        Book book4 = new Book(new ObjectId(), "Test Title 4", "Jane Doe", Genre.SCIFI, "Fake Publish", Year.of(2021), "0123456788", null);
        Book book5 = new Book(new ObjectId(), "Test Title 5", "John Doe", Genre.CRIME, "Fake Publish", Year.of(2020), "0123456789", null);
        Book book6 = new Book(new ObjectId(), "Test Title 6", "Jane Doe", Genre.DRAMA, "Fake Publish", Year.of(2021), "0123456788", null);

        List<Book> books= new ArrayList<>();
        books.add(book1);
        books.add(book2);
        books.add(book3);
        books.add(book4);
        books.add(book5);
        books.add(book6);
        return books;
    }
}
