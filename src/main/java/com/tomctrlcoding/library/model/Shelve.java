package com.tomctrlcoding.library.model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import jakarta.nosql.Column;
import jakarta.nosql.Entity;
import jakarta.nosql.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Entity("Shelves")
public final class Shelve {
    @Id
    @JsonSerialize(using = ToStringSerializer.class)
    private final ObjectId id;

    @Column
    @NotEmpty @NotBlank
    private final String name;

    @Column
    private final List<String> bookIds;


    public Shelve(String name) {
        this.id = new ObjectId();
        this.name = name;
        this.bookIds = new ArrayList<>();
    }

    public String getId(){
        return id.toString();
    }

    public String getName(){
        return name;
    }

    public List<String> getBookIds() {
        return Collections.unmodifiableList(this.bookIds);
    }

    public List<String> addBookId(String bookId) {
        Objects.requireNonNull(bookId);

        if(bookId.isEmpty() || bookId.isBlank()) {
            throw new AssertionError("bookId cannot be empty or blank");
        }

        this.bookIds.add(bookId);
        return getBookIds();
    }

    public List<String> removeBookId(String bookId) {
        Objects.requireNonNull(bookId);

        if(bookId.isEmpty() || bookId.isBlank()) {
            throw new AssertionError("bookId cannot be empty or blank");
        }

        this.bookIds.remove(bookId);
        return getBookIds();
    }

}
