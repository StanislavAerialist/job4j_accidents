package ru.job4j.accidents.repository;

import org.apache.http.annotation.ThreadSafe;
import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.AccidentType;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@ThreadSafe
public class MemAccidentTypeRepository implements AccidentTypeRepository {
    private final Map<Integer, AccidentType> accidentTypes = new ConcurrentHashMap<>();

    public MemAccidentTypeRepository() {
        accidentTypes.put(1, new AccidentType(1, "Две машины"));
        accidentTypes.put(2, new AccidentType(2, "Машина и человек"));
        accidentTypes.put(3, new AccidentType(3, "Машина и велосипед"));
    }

    @Override
    public Optional<AccidentType> findById(int id) {
        return Optional.ofNullable(accidentTypes.get(id));
    }

    @Override
    public List<AccidentType> findAll() {
        return accidentTypes.values().stream().toList();
    }
}
