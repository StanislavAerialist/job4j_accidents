package ru.job4j.accidents.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.AccidentType;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class HbmAccidentTypeRepository implements AccidentTypeRepository {
    private final CrudRepository crudRepository;

    @Override
    public Optional<AccidentType> findById(int id) {
        return crudRepository.optional(
                "from AccidentType where id = :aId", AccidentType.class, Map.of("aId", id));
    }

    @Override
    public List<AccidentType> findAll() {
        return crudRepository.query("from AccidentType", AccidentType.class);
    }
}
