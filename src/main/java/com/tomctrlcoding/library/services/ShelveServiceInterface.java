package com.tomctrlcoding.library.services;

import com.tomctrlcoding.library.model.Shelve;

import java.util.List;

public interface ShelveServiceInterface {

    List<Shelve> getAllShelves();

    Shelve findShelveByName(String name);

}
