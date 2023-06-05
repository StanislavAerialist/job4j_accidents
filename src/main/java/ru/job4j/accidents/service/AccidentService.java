package ru.job4j.accidents.service;

import ru.job4j.accidents.model.Accident;

import java.util.List;
import java.util.Optional;

public interface AccidentService {
    Optional<Accident> save(Accident accident, int typeId);
    boolean deleteById(int id);

    boolean update(Accident accident);

    Optional<Accident> findById(int id);
    List<Accident> findAll();
}