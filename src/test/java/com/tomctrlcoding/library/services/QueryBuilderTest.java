package com.tomctrlcoding.library.services;

import com.tomctrlcoding.library.model.Book;
import com.tomctrlcoding.library.model.Genre;
import jakarta.inject.Inject;
import jakarta.nosql.QueryMapper;
import jakarta.nosql.document.DocumentTemplate;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class QueryBuilderTest {

    @InjectMocks
    DocumentTemplate template;

    @InjectMocks
    private QueryBuilder queryBuilder;

    @Test
    void testBuildBookQueryAND(){
        var select = Mockito.mock(QueryMapper.MapperFrom.class);
        var query = Mockito.mock(QueryMapper.MapperWhere.class);
        var title = "Test Title";
        var author = "John Doe";
        var publisher = "Test Publish";
        var genre = Genre.MYSTERY;
        var year = "2024";

       // when(select.where("title")).thenReturn()

        var result = queryBuilder.buildBookQuery(select, title, author, genre, publisher, year, true);

        var validationQuery = validationQueryAND();

        assertEquals(result,validationQuery);
    }

    private QueryMapper.MapperFrom getSelect(){
        return Mockito.mock(QueryMapper.MapperFrom.class);
    }
    private QueryMapper.MapperWhere validationQueryAND(){
        return template.select(Book.class).where("title").eq("Test Title")
                .and("author").eq("John Doe")
                .and("publisher").eq("Test Publish")
                .and("genre").eq(Genre.MYSTERY)
                .and("publishYear").eq("2024");
    }
}
