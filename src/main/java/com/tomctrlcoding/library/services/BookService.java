package com.tomctrlcoding.library.services;

import com.tomctrlcoding.library.model.Book;
import com.tomctrlcoding.library.model.Genre;
import com.tomctrlcoding.library.repositories.BookRepositoryI;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.nosql.document.DocumentTemplate;
import org.eclipse.jnosql.mapping.Database;
import org.eclipse.jnosql.mapping.DatabaseType;

import java.util.List;

@ApplicationScoped
public class BookService implements BookServiceInterface{

    // Logger logger = Logger.getLogger("BookService");
    @Inject
    @Database(DatabaseType.DOCUMENT)
    private BookRepositoryI bookRepository;

    @Inject
    DocumentTemplate template;

    @Override
    public List<Book> getAllBooks() {
        var bookStream = bookRepository.findAll();
        List<Book> books = bookStream.toList();
        return books;
    }

    @Override
    public Book findBookById(String id) {
        return bookRepository.findById(id).orElseThrow();
    }

    @Override
    public List<Book> findBooksByGenre(Genre genre) {
        List<Book> books = template.select(Book.class).where("genre").eq(genre).result();
        return books;
    }

    @Override
    public List<Book> findBooksByQueryParams(String author, String genre) {
        List<Book> books = bookRepository.findByAuthorOrGenre(author, genre);
        return books;
    }
}
