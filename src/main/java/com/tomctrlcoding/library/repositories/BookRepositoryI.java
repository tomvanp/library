package com.tomctrlcoding.library.repositories;

import com.tomctrlcoding.library.model.Book;
import com.tomctrlcoding.library.model.Genre;

import jakarta.data.repository.BasicRepository;
import jakarta.data.repository.Repository;


import java.util.List;


@Repository
public interface BookRepositoryI extends BasicRepository<Book,String> {

    List<Book> findByAuthorOrGenre(String author, Genre genre);
}
