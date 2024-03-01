package com.tomctrlcoding.library.services;

import com.tomctrlcoding.library.model.Book;
import com.tomctrlcoding.library.model.Genre;
import org.bson.types.ObjectId;

import java.util.List;

public interface BookServiceInterface {

    List<Book> getAllBooks();

    Book findBookById(ObjectId id);

    List<Book> findBooksByGenre(Genre genre);

    List<Book> findBooksByQueryParams(String author, Genre genre);

    Book insertNewBook(Book book);

    void deleteBook(ObjectId bookId);
}
