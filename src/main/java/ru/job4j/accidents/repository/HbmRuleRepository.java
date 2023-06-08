package ru.job4j.accidents.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.Rule;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Repository
@AllArgsConstructor
public class HbmRuleRepository implements RuleRepository {
    private final CrudRepository crudRepository;

    @Override
    public Optional<Rule> findById(int id) {
        return crudRepository.optional(
                "from Rule where id = :rId", Rule.class, Map.of("rId", id));
    }

    @Override
    public List<Rule> findAll() {
        return crudRepository.query("from Rule r ORDER BY r.id ASC", Rule.class);
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
