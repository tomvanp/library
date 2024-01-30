package com.tomctrlcoding.library.services;

import com.tomctrlcoding.library.model.Book;
import com.tomctrlcoding.library.repositories.BookRepositoryI;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.jnosql.mapping.Database;
import org.eclipse.jnosql.mapping.DatabaseType;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@ApplicationScoped
public class BookService implements BookServiceInterface{

    Logger logger = Logger.getLogger("BookService");
    @Inject
    @Database(DatabaseType.DOCUMENT)
    private BookRepositoryI bookRepository;

    @Override
    public List<Book> getAllBooks() {
        var bookStream = bookRepository.findAll();
        List<Book> books = bookStream.toList();
        logger.log(Level.INFO, "books from mongodb", books);
        return books;
    }
}
