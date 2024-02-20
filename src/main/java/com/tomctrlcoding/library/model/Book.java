package com.tomctrlcoding.library.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.YearDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.YearSerializer;
import jakarta.nosql.Column;
import jakarta.nosql.Entity;
import jakarta.nosql.Id;
import org.bson.types.ObjectId;

import java.time.Year;
import java.util.Objects;

@Entity
public record Book(@Id
                   ObjectId id,
                   @Column("title") String title,
                   @Column("author")String author,
                   @Column("genre") Genre genre,
                   @Column("publisher") String publisher,
                   @JsonDeserialize(using = YearDeserializer.class)
                   @JsonSerialize(using = YearSerializer.class)
                   @Column("publishYear") Year publishYear,
                   @Column("isbn") String isbn) {

    public Book {
        id = Objects.requireNonNullElse(id, new ObjectId());
        Objects.requireNonNull(title);
        Objects.requireNonNull(author);
        Objects.requireNonNull(genre);
        Objects.requireNonNull(publisher);
        Objects.requireNonNull(publishYear);
        Objects.requireNonNull(isbn);

        if( title.isEmpty() ) throw new IllegalArgumentException();
        if( author.isEmpty() ) throw new IllegalArgumentException();
        if( publisher.isEmpty() ) throw new IllegalArgumentException();
        if( isbn.isEmpty() ) throw new IllegalArgumentException();
    }
}
