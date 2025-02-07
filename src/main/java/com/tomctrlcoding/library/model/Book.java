package com.tomctrlcoding.library.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.fasterxml.jackson.datatype.jsr310.deser.YearDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.YearSerializer;
import com.tomctrlcoding.library.validation.constraints.ISBN;
import jakarta.nosql.Column;
import jakarta.nosql.Entity;
import jakarta.nosql.Id;
import jakarta.validation.constraints.*;
import org.bson.types.ObjectId;

import java.time.Year;
import java.util.Objects;

@Entity
public record Book(@Id
                   @JsonSerialize(using = ToStringSerializer.class)
                   ObjectId id,
                   @Column
                   @NotEmpty @NotBlank
                   String title,
                   @Column
                   @NotEmpty @NotBlank
                   String author,
                   @Column
                   @NotNull
                   Genre genre,
                   @Column
                   @NotEmpty @NotBlank
                   String publisher,
                   @JsonDeserialize(using = YearDeserializer.class)
                   @JsonSerialize(using = YearSerializer.class)
                   @PastOrPresent
                   @Column
                   Year publishYear,
                   @Column
                   @NotEmpty @NotBlank
                   @ISBN(message = "Invalid ISBN. Check size is 10 or 13 digits. Exclude the text 'ISBN'.")
                   @Size(max = 20)
                   String isbn)

{

    public Book {
        id = Objects.requireNonNullElse(id, new ObjectId());
    }
}
