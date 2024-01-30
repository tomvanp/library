package com.tomctrlcoding.library.repositories;

import com.tomctrlcoding.library.model.Book;
import jakarta.data.repository.PageableRepository;
import jakarta.data.repository.Repository;

@Repository
public interface BookRepositoryI extends PageableRepository<Book,Long> {
}
