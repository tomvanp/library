package com.tomctrlcoding.library.repositories;

import com.tomctrlcoding.library.model.Shelve;
import jakarta.data.repository.BasicRepository;
import jakarta.data.repository.Repository;
import org.bson.types.ObjectId;

import java.util.Optional;

@Repository
public interface ShelveRepository extends BasicRepository<Shelve, ObjectId> {

    Optional<Shelve> findByName(String name);
}
