package com.tomctrlcoding.library.services;

import com.tomctrlcoding.library.model.Book;
import com.tomctrlcoding.library.model.Genre;
import com.tomctrlcoding.library.repositories.BookRepositoryI;
import com.tomctrlcoding.library.validation.constraints.ISBN;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.nosql.document.DocumentTemplate;
import jakarta.ws.rs.NotFoundException;
import org.bson.types.ObjectId;
import org.eclipse.jnosql.mapping.Database;
import org.eclipse.jnosql.mapping.DatabaseType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class BookService implements BookServiceInterface{

    Logger logger = LoggerFactory.getLogger("BookService");
    @Inject
    @Database(DatabaseType.DOCUMENT)
    private BookRepositoryI bookRepository;

    @Inject
    private DocumentTemplate template;

    @Inject
    private QueryBuilder queryBuilder;

   // @Inject
    //private CriteriaDocumentTemplate docTemp;

    @Override
    public List<Book> getAllBooks() {
        var bookStream = bookRepository.findAll();
        return bookStream.toList();
    }

    @Override
    public Book findBookById(ObjectId id) {
        return bookRepository.findById(id).orElseThrow();
    }

    @Override
    public Book findBookByISBN(@ISBN String isbn){

        Optional<Book> book = template.select(Book.class).where("isbn").eq(isbn).singleResult();

        return book.orElseThrow(NotFoundException::new);
    }

    @Override
    public List<Book> findBooksByQueryParams(String title,
                                             String author,
                                             Genre genre,
                                             String publisher,
                                             String publishYear,
                                             boolean andCheck) {

        var select = template.select(Book.class);
        var query = queryBuilder.buildBookQuery(select, title, author, genre, publisher, publishYear, andCheck);

        return queryBuilder.result(query);

    }

    @Override
    public Book insertNewBook(Book book) {

        return template.insert(book);
    }
    @Override
    public void deleteBook(ObjectId bookId) {
        template.delete(Book.class, bookId);
    }
}
