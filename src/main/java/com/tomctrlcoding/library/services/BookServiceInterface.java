package com.tomctrlcoding.library.services;

import com.tomctrlcoding.library.model.Book;
import com.tomctrlcoding.library.model.Genre;

import java.util.List;

public interface BookServiceInterface {

    List<Book> getAllBooks();

    Book findBookById(String id);

    List<Book> findBooksByGenre(Genre genre);

    List<Book> findBooksByQueryParams(String author, Genre genre);

    Book insertNewBook(Book book);
}
