package com.tomctrlcoding.library.services;

import com.tomctrlcoding.library.model.Book;
import com.tomctrlcoding.library.model.Genre;
import com.tomctrlcoding.library.validation.constraints.ISBN;
import org.bson.types.ObjectId;

import java.util.List;

public interface BookServiceInterface {

    List<Book> getAllBooks();

    Book findBookById(ObjectId id);

    Book findBookByISBN(@ISBN String isbn);

    List<Book> findBooksByQueryParams(String title,
                                      String author,
                                      Genre genre,
                                      String publisher,
                                      String publishYear,
                                      boolean andOrCheck);

    Book insertNewBook(Book book);

    void deleteBook(ObjectId bookId);
}
