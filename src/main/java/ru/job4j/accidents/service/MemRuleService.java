package ru.job4j.accidents.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.accidents.model.Rule;
import ru.job4j.accidents.repository.JpaRuleRepository;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class MemRuleService implements RuleService {
    private final JpaRuleRepository ruleRepository;

    @Override
    public Optional<Rule> findById(int id) {
        return ruleRepository.findById(id);
    }

    @Override
    public List<Rule> findAll() {
        return (List<Rule>) ruleRepository.findAll();
    }
}
