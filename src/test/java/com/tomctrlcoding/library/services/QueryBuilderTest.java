package com.tomctrlcoding.library.services;

import com.tomctrlcoding.library.model.Genre;
import jakarta.nosql.QueryMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Year;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

/**
 * VERY BAD TEST CASES
 * These tests are not representative because of QueryMapper classes from JNOSQL aren't correctly mockable.
 * In this case there is the issue of a mock method call returning another mock which in turn returns another mock.
 */
@ExtendWith(MockitoExtension.class)
public class QueryBuilderTest {

    private QueryMapper.MapperFrom from;
    private QueryMapper.MapperWhere where;
    private QueryMapper.MapperNameCondition condition;

    @InjectMocks
    private QueryBuilder queryBuilder;

    @BeforeEach
    void setUpMocks() {
        from = Mockito.mock(QueryMapper.MapperFrom.class);
        where = Mockito.mock(QueryMapper.MapperWhere.class);
        condition = Mockito.mock(QueryMapper.MapperNameCondition.class);
    }

    @Test
    void testBuildBookQueryOnTitle() {

        var title = "Test Title";

        when(from.where(anyString())).thenReturn(condition);
        when(condition.eq(anyString())).thenReturn(where);

        queryBuilder.buildBookQuery(from, title, null, null, null, null, false);

        verify(from, times(1)).where(anyString());
        verify(condition, times(1)).eq(anyString());

    }

    @Test
    void testBuildBookQueryOnAuthor() {

        var author = "John Doe";

        when(from.where(anyString())).thenReturn(condition);
        when(condition.eq(anyString())).thenReturn(where);

        queryBuilder.buildBookQuery(from, null, author, null, null, null, false);

        verify(from, times(1)).where(anyString());
        verify(condition, times(1)).eq(anyString());

    }

    @Test
    void testBuildBookQueryOnGenre() {

        var genre = Genre.MYSTERY;

        when(from.where(anyString())).thenReturn(condition);
        when(condition.eq(any(Genre.class))).thenReturn(where);

        queryBuilder.buildBookQuery(from, null, null, genre, null, null, false);

        verify(from, times(1)).where(anyString());
        verify(condition, times(1)).eq(any(Genre.class));

    }

    @Test
    void testBuildBookQueryOnPublisher() {

        var publisher = "Test Publish";

        when(from.where(anyString())).thenReturn(condition);
        when(condition.eq(anyString())).thenReturn(where);

        queryBuilder.buildBookQuery(from, null, null, null, publisher, null, false);

        verify(from, times(1)).where(anyString());
        verify(condition, times(1)).eq(anyString());

    }

    @Test
    void testBuildBookQueryOnYear() {

        var year = "2024";

        when(from.where(anyString())).thenReturn(condition);
        when(condition.eq(any(Year.class))).thenReturn(where);

        queryBuilder.buildBookQuery(from, null, null, null, null, year, false);

        verify(from, times(1)).where(anyString());
        verify(condition, times(1)).eq(any(Year.class));

    }

    @Test
    void testBuildBookQueryOnAllParametersOR() {

        var title = "Test Title";
        var author = "John Doe";
        var publisher = "Test Publish";
        var genre = Genre.MYSTERY;
        var year = "2024";

        when(from.where(anyString())).thenReturn(condition);
        when(where.or(anyString())).thenReturn(condition);
        when(condition.eq(any())).thenReturn(where);

        queryBuilder.buildBookQuery(from, title, author, genre, publisher, year, false);

        verify(from, times(1)).where(anyString());
        verify(where,times(4)).or(anyString());
        verify(condition, times(5)).eq(any());

    }

    @Test
    void testBuildBookQueryOnAllParametersAND() {

        var title = "Test Title";
        var author = "John Doe";
        var publisher = "Test Publish";
        var genre = Genre.MYSTERY;
        var year = "2024";

        when(from.where(anyString())).thenReturn(condition);
        when(where.and(anyString())).thenReturn(condition);
        when(condition.eq(any())).thenReturn(where);

        queryBuilder.buildBookQuery(from, title, author, genre, publisher, year, true);

        verify(from, times(1)).where(anyString());
        verify(where,times(4)).and(anyString());
        verify(condition, times(5)).eq(any());

    }
}
