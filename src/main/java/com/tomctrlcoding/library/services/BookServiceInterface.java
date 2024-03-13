package com.tomctrlcoding.library.services;

import com.tomctrlcoding.library.model.Book;
import com.tomctrlcoding.library.model.Genre;
import org.bson.types.ObjectId;

import java.time.Year;
import java.util.List;

public interface BookServiceInterface {

    List<Book> getAllBooks();

    Book findBookById(ObjectId id);

    Book findBookByISBN(String isbn);

    List<Book> findBooksByGenre(Genre genre);

    List<Book> findBooksByQueryParams(String title,
                                      String author,
                                      Genre genre,
                                      String publisher,
                                      String publishYear,
                                      boolean andOrCheck);

    Book insertNewBook(Book book);

    void deleteBook(ObjectId bookId);
}
