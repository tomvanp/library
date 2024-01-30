package com.tomctrlcoding.library.model;

import jakarta.nosql.Column;
import jakarta.nosql.Entity;
import jakarta.nosql.Id;
import org.eclipse.jnosql.databases.mongodb.mapping.ObjectIdConverter;
import org.eclipse.jnosql.mapping.Convert;

import java.time.Year;

@Entity
public record Book(@Id
                   @Convert(ObjectIdConverter.class) String id,
                   @Column("title") String title,
                   @Column("author")String author,
                   @Column("genre") Genre genre,
                   @Column("publisher") String publisher,
                   @Column("publishYear") Year publishYear,
                   @Column("isbn") String isbn) {
}
