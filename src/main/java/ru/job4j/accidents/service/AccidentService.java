package ru.job4j.accidents.service;

import ru.job4j.accidents.model.Accident;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface AccidentService {
    Optional<Accident> save(Accident accident, int typeId, Set<Integer> rIds);
    boolean deleteById(int id);

    boolean update(Accident accident, Set<Integer> rIds);

    Optional<Accident> findById(int id);
    List<Accident> findAll();
}
