package com.tomctrlcoding.library.services;

import com.tomctrlcoding.library.model.Book;
import com.tomctrlcoding.library.model.Genre;
import com.tomctrlcoding.library.repositories.BookRepositoryI;
import jakarta.nosql.QueryMapper;
import jakarta.nosql.document.DocumentTemplate;
import jakarta.ws.rs.NotFoundException;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
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

    @Mock
    private QueryBuilder queryBuilder;

    @InjectMocks
    private BookService bookService;

    @Test
     void testGetAllBooks(){
        List<Book> books = bookList();
        when(bookRepo.findAll()).thenReturn(books.stream());

        var result = bookService.getAllBooks();

        assertIterableEquals(books, result);

        verify(bookRepo).findAll();
    }

    @Test
    void testFindBookByID(){
        var id = new ObjectId();
        Book book = createTestBook(id);
        when(bookRepo.findById(id)).thenReturn(Optional.of(book));

        var result = bookService.findBookById(id);

        assertEquals(book, result);

        verify(bookRepo).findById(id);
    }

    /**
     * Issue with this test is the fact that a mock returns another mock
     * this is because QueryMapper class is difficult to mock
     */
    @Test
    void testFindBookByISBN() {
        var id = new ObjectId();
        Book book = createTestBook(id);

        var from = Mockito.mock(QueryMapper.MapperFrom.class);
        var where = Mockito.mock(QueryMapper.MapperWhere.class);
        var condition = Mockito.mock(QueryMapper.MapperNameCondition.class);
        when(template.select(Book.class)).thenReturn(from);
        when(from.where(anyString())).thenReturn(condition);
        when(condition.eq(anyString())).thenReturn(where);
        when(where.singleResult()).thenReturn(Optional.of(book));

        var result = bookService.findBookByISBN("978-0-5521-3325-8");

        assertEquals(book, result);
    }

    /**
     * Issue with this test is the fact that a mock returns another mock
     * this is because QueryMapper class is difficult to mock
     */
    @Test
    void testFindBookByISBNNotFoundException() {

        var from = Mockito.mock(QueryMapper.MapperFrom.class);
        var where = Mockito.mock(QueryMapper.MapperWhere.class);
        var condition = Mockito.mock(QueryMapper.MapperNameCondition.class);
        when(template.select(Book.class)).thenReturn(from);
        when(from.where(anyString())).thenReturn(condition);
        when(condition.eq(anyString())).thenReturn(where);
        when(where.singleResult()).thenReturn(Optional.empty());

        Assertions.assertThrows(NotFoundException.class, () -> {
            bookService.findBookByISBN("978-0-5521-3325-8");
        }, "NotFoundException was expected");

    }

    /**
     * Issue with this test is the fact that a mock returns another mock
     * this is because QueryMapper class is difficult to mock
     */
    @Test
    void testQueryBooksAllParams() {
        var title = "Test Title";
        var author = "John Doe";
        var publisher = "Test Publish";
        var genre = Genre.MYSTERY;
        var year = "2024";

        var book = createTestBook(new ObjectId());
        List<Book> books = new ArrayList<>();
        books.add(book);

        var from = Mockito.mock(QueryMapper.MapperFrom.class);
        var where = Mockito.mock(QueryMapper.MapperWhere.class);
        when(template.select(Book.class)).thenReturn(from);
        when(queryBuilder.buildBookQuery(any(QueryMapper.MapperFrom.class), anyString(), anyString(), any(Genre.class), anyString(), anyString(), anyBoolean()))
                .thenReturn(where);
        when(queryBuilder.result(any(QueryMapper.MapperWhere.class))).thenReturn(books);

        var result = bookService.findBooksByQueryParams(title, author, genre, publisher, year, false);

        assertEquals(1, result.size());
        verify(template, times(1)).select(Book.class);
        verify(queryBuilder, times(1)).buildBookQuery(any(QueryMapper.MapperFrom.class), anyString(), anyString(), any(Genre.class), anyString(), anyString(), anyBoolean());
        verify(queryBuilder, times(1)).result(any(QueryMapper.MapperWhere.class));

    }

    @Test
    void testInsertBook(){
        Book book = createTestBook(new ObjectId());

        when(template.insert(book)).thenReturn(book);

        var result = bookService.insertNewBook(book);

        assertEquals(book, result);

        verify(template).insert(book);
    }

    @Test
    void testDeleteBook(){
        var id = new ObjectId();
        doNothing().when(template).delete(Book.class, id);

        bookService.deleteBook(id);

        verify(template).delete(Book.class, id);
    }

    private Book createTestBook(ObjectId id) {
        var title = "Test Title";
        var author = "John Doe";
        var publisher = "Test Publish";
        var genre = Genre.MYSTERY;
        var year = "2024";

        return new Book(id, title, author, genre, publisher, Year.parse(year), "978-0-5521-3325-8");
    }
    private List<Book> bookList(){
        Book book1 = new Book(new ObjectId(), "Test Title 1", "John Doe", Genre.MYSTERY, "Fake Publish", Year.of(2020), "0123456789");
        Book book2 = new Book(new ObjectId(), "Test Title 2", "Jane Doe", Genre.DRAMA, "Fake Publish", Year.of(2021), "0123456788");
        Book book3 = new Book(new ObjectId(), "Test Title 3", "John Doe", Genre.MYSTERY, "Fake Publish", Year.of(2020), "0123456789");
        Book book4 = new Book(new ObjectId(), "Test Title 4", "Jane Doe", Genre.SCIFI, "Fake Publish", Year.of(2021), "0123456788");
        Book book5 = new Book(new ObjectId(), "Test Title 5", "John Doe", Genre.CRIME, "Fake Publish", Year.of(2020), "0123456789");
        Book book6 = new Book(new ObjectId(), "Test Title 6", "Jane Doe", Genre.DRAMA, "Fake Publish", Year.of(2021), "0123456788");

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
