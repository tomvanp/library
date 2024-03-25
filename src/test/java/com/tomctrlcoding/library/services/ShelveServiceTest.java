package com.tomctrlcoding.library.services;

import com.tomctrlcoding.library.model.Shelve;
import com.tomctrlcoding.library.repositories.ShelveRepository;
import jakarta.ws.rs.NotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ShelveServiceTest {

    @Mock
    private ShelveRepository repository;

    @InjectMocks
    private ShelveService shelveService;

    @Test
    void testGetAllShelves() {
        var shelves = createTestList();

        when(repository.findAll()).thenReturn(shelves.stream());

        var result = shelveService.getAllShelves();

        assertIterableEquals(shelves, result);
        verify(repository, times(1)).findAll();
    }

    @Test
    void testFindShelveByName() {
        var shelve = createTestShelve();

        when(repository.findByName(anyString())).thenReturn(Optional.of(shelve));

        var result = shelveService.findShelveByName(shelve.getName());

        assertEquals(shelve, result);
        verify(repository, times(1)).findByName(anyString());
    }

    @Test
    void testFindShelveByNameNotFound() {
        when(repository.findByName(anyString())).thenThrow(NotFoundException.class);

        Assertions.assertThrows(NotFoundException.class, () -> shelveService.findShelveByName("Test"), "NotFoundException was expected");

        verify(repository, times(1)).findByName(anyString());
    }

    private Shelve createTestShelve() {
        var shelve = new Shelve("Test");
        shelve.addBookId("1");

        return shelve;
    }

    private List<Shelve> createTestList() {
        List<Shelve> shelves = new ArrayList<>();
        shelves.add(createTestShelve());
        return shelves;
    }
}
