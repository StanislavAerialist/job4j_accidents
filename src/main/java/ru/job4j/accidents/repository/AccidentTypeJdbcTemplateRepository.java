package ru.job4j.accidents.repository;

import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.AccidentType;
import ru.job4j.accidents.util.AccidentTypeRowMapper;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
public class AccidentTypeJdbcTemplateRepository implements AccidentTypeRepository {
    private final JdbcTemplate jdbc;

    @Override
    public Optional<AccidentType> findById(int typeId) {
        AccidentType accidentType = jdbc.queryForObject(
                "SELECT * FROM types WHERE id = ?",
                new AccidentTypeRowMapper(), typeId);
        return Optional.ofNullable(accidentType);
    }

    @Override
    public List<AccidentType> findAll() {
        return jdbc.query(
                "SELECT * FROM types",
                new AccidentTypeRowMapper());
    }
}