package ru.job4j.accidents.repository;

import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.Rule;
import ru.job4j.accidents.util.RuleRowMapper;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Repository
@AllArgsConstructor
public class RuleJdbcTemplateRepository implements RuleRepository {
    private final JdbcTemplate jdbc;

    @Override
    public Optional<Rule> findById(int ruleId) {
        Rule rule = jdbc.queryForObject("SELECT * FROM rules WHERE id = ?", new RuleRowMapper(), ruleId);
        return Optional.ofNullable(rule);
    }

    @Override
    public List<Rule> findAll() {
        return jdbc.query("SELECT * FROM rules",
                new RuleRowMapper());
    }

    @Override
    public Set<Rule> findByIds(Set<Integer> rIds) {
        return rIds.stream()
                .map(this::findById)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toSet());
    }
}