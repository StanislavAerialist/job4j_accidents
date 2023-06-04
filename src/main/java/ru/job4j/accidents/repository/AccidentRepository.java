package ru.job4j.accidents.repository;

import ru.job4j.accidents.model.Accident;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface AccidentRepository {
    Accident save(Accident accident);
    boolean deleteById(int id);

    boolean update(Accident accident);

    Optional<Accident> findById(int id);

    List<Accident> findAll();
}
