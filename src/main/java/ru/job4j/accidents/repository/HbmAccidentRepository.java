package ru.job4j.accidents.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.Accident;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class HbmAccidentRepository implements AccidentRepository {
    private final CrudRepository crudRepository;

    @Override
    public Accident save(Accident accident) {
        crudRepository.run(session -> session.save(accident));
        return accident;
    }

    @Override
    public boolean deleteById(int id) {
        return crudRepository.runForBoolean("DELETE Accident WHERE id = :aId", Map.of("aId", id));
    }

    @Override
    public boolean update(Accident accident) {
        return crudRepository.runForBoolean(session -> {
            session.merge(accident);
            return true;
        });
    }

    @Override
    public Optional<Accident> findById(int id) {
        return crudRepository.optional(
                "from Accident a LEFT JOIN FETCH a.type LEFT JOIN FETCH a.rules where a.id = :aId",
                Accident.class, Map.of("aId", id));
    }

    @Override
    public List<Accident> findAll() {
        return crudRepository.query("from Accident a LEFT JOIN FETCH a.type LEFT JOIN FETCH a.rules ORDER BY a.id ASC",
                Accident.class);
    }
}
