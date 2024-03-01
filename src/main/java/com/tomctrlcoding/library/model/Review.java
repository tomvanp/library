package com.tomctrlcoding.library.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import jakarta.nosql.Column;
import jakarta.validation.constraints.*;

import java.time.LocalDate;
import java.util.Objects;

public record Review (
        @Column
        String review,
        @Column
        @Min(value = 1)
        @Max(value = 5)
        int rating,
        @Column
        @FutureOrPresent
        @JsonDeserialize(using = LocalDateDeserializer.class)
        @JsonSerialize(using = LocalDateSerializer.class)
        LocalDate reviewDate

) {
        public Review {
                reviewDate = Objects.requireNonNullElse(reviewDate,LocalDate.now());
        }
}
