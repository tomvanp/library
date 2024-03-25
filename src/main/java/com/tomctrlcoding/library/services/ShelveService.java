package com.tomctrlcoding.library.services;

import com.tomctrlcoding.library.model.Shelve;
import com.tomctrlcoding.library.repositories.ShelveRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.NotFoundException;
import org.eclipse.jnosql.mapping.Database;
import org.eclipse.jnosql.mapping.DatabaseType;

import java.util.List;

@ApplicationScoped
public class ShelveService implements ShelveServiceInterface{

    @Inject
    @Database(DatabaseType.DOCUMENT)
    private ShelveRepository repository;

    @Override
    public List<Shelve> getAllShelves() {
        return repository.findAll().toList();
    }

    @Override
    public Shelve findShelveByName(String name) {
        return repository.findByName(name).orElseThrow(NotFoundException::new);
    }
}
