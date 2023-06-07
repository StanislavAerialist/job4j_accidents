package ru.job4j.accidents.repository;

import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.model.Rule;
import ru.job4j.accidents.util.AccidentRowMapper;

import java.sql.PreparedStatement;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class AccidentJdbcTemplateRepository implements AccidentRepository {
    private final JdbcTemplate jdbc;

    /**
     * Сохранение модели Accident.
     * KeyHolder возвращает сгенерированный ID accident
     * @param accident Accident
     * @return Accident ID > 0;
     */
    @Override
    public Accident save(Accident accident) {
        KeyHolder key = new GeneratedKeyHolder();
        final String INSERT_SQL = "INSERT INTO accidents(name, text, address, type_id) VALUES (?, ?, ?, ?)";
        jdbc.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(INSERT_SQL, new String[]{"id"});
            ps.setString(1, accident.getName());
            ps.setString(2, accident.getText());
            ps.setString(3, accident.getAddress());
            ps.setInt(4, accident.getType().getId());
            return ps;
        }, key);
        accident.setId(key.getKey().intValue());
        saveAccidentsRules(accident);
        return accident;
    }

    /*
     * Сохранение в таблицу ACCIDENTS_RULES данных по статьям присвоенным ACCIDENT
     *
     * @param accident Accident
     */
    private void saveAccidentsRules(Accident accident) {
        for (Rule rule : accident.getRules()) {
            jdbc.update("INSERT INTO accidents_rules(accident_id, rule_id) VALUES (?, ?)",
                    accident.getId(), rule.getId());
        }
    }

    @Override
    public boolean deleteById(int id) {
        deleteAccidentRules(id);
        int result = jdbc.update("DELETE FROM accidents AS ac WHERE ac.id = ?", id);
        return result > 0;
    }

    /*
     * Удаление из таблицы Accidents_Rules записи по Accident ID
     *
     * @param id - Accident ID
     */
    private void deleteAccidentRules(int id) {
        jdbc.update("DELETE FROM accidents_rules AS ar WHERE ar.accident_id = ?", id);
    }


    @Override
    public boolean update(Accident accident) {
        int result = jdbc.update("UPDATE accidents SET name = ?, text = ?, address = ?, type_id = ? WHERE id = ?",
            accident.getName(), accident.getText(), accident.getAddress(), accident.getType().getId(), accident.getId());
        if (result > 0) {
            deleteAccidentRules(accident.getId());
            saveAccidentsRules(accident);
        }
        return result > 0;
    }

    @Override
    public Optional<Accident> findById(int id) {
        Map<Integer, Accident> accidentMap = new HashMap<>();
        jdbc.query("SELECT * FROM accidents AS ac "
                        + "LEFT JOIN types AS t ON ac.type_id = t.id "
                        + "LEFT JOIN accidents_rules AS ar ON ac.id = ar.accident_id "
                        + "LEFT JOIN rules AS r ON ar.rule_id = r.id "
                        + "WHERE ac.id = ?",
                new AccidentRowMapper(accidentMap), id);
        return Optional.ofNullable(accidentMap.get(id));
    }

    @Override
    public List<Accident> findAll() {
        Map<Integer, Accident> accidentMap = new HashMap<>();
        jdbc.query(
                "SELECT * FROM accidents AS ac "
                        + "LEFT JOIN types AS t ON ac.type_id = t.id "
                        + "LEFT JOIN accidents_rules AS ar ON ac.id = ar.accident_id "
                        + "LEFT JOIN rules AS r ON ar.rule_id = r.id",
                new AccidentRowMapper(accidentMap)
        );
        return accidentMap.values().stream().toList();
    }
}
