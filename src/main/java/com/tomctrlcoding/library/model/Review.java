package com.tomctrlcoding.library.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import jakarta.nosql.Column;
import jakarta.nosql.Entity;
import jakarta.nosql.Id;
import jakarta.validation.constraints.*;
import org.bson.types.ObjectId;

import java.time.LocalDate;
import java.util.Objects;

@Entity("Reviews")
public record Review (

        @Id
        @JsonSerialize(using = ToStringSerializer.class)
        ObjectId id,

        @Column
        @NotBlank @NotEmpty
        String bookId,
        @Column
        @Size(max = 200, message = "Review has a 200 character limit")
        @NotBlank @NotEmpty
        String review,
        @Column
        @Min(value = 0, message = "A rating has a minimum value of 0")
        @Max(value = 5, message = "A rating has a maximum value of 5")
        int rating,
        @Column
        @JsonDeserialize(using = LocalDateDeserializer.class)
        @JsonSerialize(using = LocalDateSerializer.class)
        LocalDate reviewDate

) {
        public Review {
                reviewDate = Objects.requireNonNullElse(reviewDate,LocalDate.now());
        }
}
