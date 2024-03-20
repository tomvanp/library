package com.tomctrlcoding.library.services;

import com.tomctrlcoding.library.model.Book;
import com.tomctrlcoding.library.model.Genre;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.nosql.QueryMapper;

import java.time.Year;
import java.util.List;
import java.util.Objects;

@ApplicationScoped
class QueryBuilder {

    private QueryMapper.MapperWhere query;

    public QueryMapper.MapperWhere buildBookQuery(QueryMapper.MapperFrom select,
                                              String title,
                                              String author,
                                              Genre genre,
                                              String publisher,
                                              String publishYear,
                                              boolean andCheck) {

        if (Objects.nonNull(title) && !title.isEmpty() && !title.isBlank()){
            query = select.where("title").eq(title);
        }

        if (Objects.nonNull(author) && !author.isEmpty() && !author.isBlank()){
            final String AUTHOR = "author";
            query = Objects.nonNull(query) ?
                    andCheck ? query.and(AUTHOR).eq(author) : query.or(AUTHOR).eq(author)
                    : select.where(AUTHOR).eq(author);
        }

        if (Objects.nonNull(genre)){
            final String GENRE = "genre";
            query = Objects.nonNull(query) ?
                    andCheck ? query.and(GENRE).eq(genre) : query.or(GENRE).eq(genre)
                    : select.where(GENRE).eq(genre);
        }

        if (Objects.nonNull(publisher) && !publisher.isEmpty() && !publisher.isBlank()){
            final String PUBLISHER = "publisher";
            query = Objects.nonNull(query) ?
                    andCheck ? query.and(PUBLISHER).eq(publisher) : query.or(PUBLISHER).eq(publisher)
                    : select.where(PUBLISHER).eq(publisher);
        }

        if (Objects.nonNull(publishYear)){
            final String PUBLISH_YEAR = "publishYear";
            var year = Year.parse(publishYear);
            query = Objects.nonNull(query) ?
                    andCheck ? query.and(PUBLISH_YEAR).eq(year) : query.or(PUBLISH_YEAR).eq(year)
                    : select.where(PUBLISH_YEAR).eq(year);
        }

        return query;

    }

    public List<Book> result(QueryMapper.MapperWhere query) {
        Objects.requireNonNull(query);
        return query.result();
    }

}
