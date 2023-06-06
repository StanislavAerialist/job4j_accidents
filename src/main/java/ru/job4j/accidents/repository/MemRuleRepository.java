package ru.job4j.accidents.repository;

import org.apache.http.annotation.ThreadSafe;
import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.AccidentType;
import ru.job4j.accidents.model.Rule;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@ThreadSafe
@Repository
public class MemRuleRepository implements RuleRepository {
    private final Map<Integer, Rule> rules = new ConcurrentHashMap<>();

    public MemRuleRepository() {
        rules.put(1, new Rule(1, "Статья 1"));
        rules.put(2, new Rule(2, "Статья 2"));
        rules.put(3, new Rule(3, "Статья 3"));
    }

    @Override
    public Optional<Rule> findById(int id) {
        return Optional.ofNullable(rules.get(id));
    }

    @Override
    public List<Rule> findAll() {
        return rules.values().stream().toList();
    }

    public Set<Rule> filter(Set<Integer> rIds) {
        return rIds.stream()
                .map(this::findById)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toSet());
    }
}
