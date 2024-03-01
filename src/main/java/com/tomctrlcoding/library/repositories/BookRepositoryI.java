package com.tomctrlcoding.library.repositories;

import com.tomctrlcoding.library.model.Book;
import com.tomctrlcoding.library.model.Genre;

import jakarta.data.repository.BasicRepository;
import jakarta.data.repository.Repository;
import org.bson.types.ObjectId;


import java.util.List;


@Repository
public interface BookRepositoryI extends BasicRepository<Book, ObjectId> {

    List<Book> findByAuthorOrGenre(String author, Genre genre);
}
