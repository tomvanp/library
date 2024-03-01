package com.tomctrlcoding.library.services;

import com.tomctrlcoding.library.model.Book;
import com.tomctrlcoding.library.model.Genre;
import com.tomctrlcoding.library.repositories.BookRepositoryI;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.nosql.QueryMapper;
import jakarta.nosql.document.DocumentTemplate;
import org.bson.types.ObjectId;
import org.eclipse.jnosql.communication.document.DocumentCondition;
import org.eclipse.jnosql.communication.document.DocumentQuery;
import org.eclipse.jnosql.mapping.Database;
import org.eclipse.jnosql.mapping.DatabaseType;

import java.util.List;
import java.util.Objects;

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
    public Book findBookById(ObjectId id) {
        return bookRepository.findById(id).orElseThrow();
    }

    @Override
    public List<Book> findBooksByGenre(Genre genre) {
        List<Book> books = template.select(Book.class).where("genre").eq(genre).result();
        return books;
    }

    @Override
    public List<Book> findBooksByQueryParams(String author, Genre genre) {
        List<Book> books = bookRepository.findByAuthorOrGenre(author, genre);


        //TODO experiment with this DocumentQuery builder and figure out DocumentCondition
/*        var queryFrom = template.select(Book.class);
        DocumentQuery.DocumentQueryBuilder query = DocumentQuery.builder();
        query = query.select().from("Book");

         DocumentQuery.DocumentQueryBuilder test = query.select().from("Book");

        if (Objects.nonNull(genre)){
             query = test.where("id").eq(genre);
        }
        if (!author.isBlank() && !author.isEmpty()) {
             queryWhere = queryWhere.or("author").eq(author);
        }
                template.select(Book.class)
                .where("id")
                .gte(10)
                .result();

        */
        return books;
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
