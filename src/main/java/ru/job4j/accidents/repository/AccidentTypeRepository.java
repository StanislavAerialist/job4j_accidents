package ru.job4j.accidents.repository;

import ru.job4j.accidents.model.AccidentType;

import java.util.List;
import java.util.Optional;

public interface AccidentTypeRepository {
    Optional<AccidentType> findById(int id);

    List<AccidentType> findAll();
}
