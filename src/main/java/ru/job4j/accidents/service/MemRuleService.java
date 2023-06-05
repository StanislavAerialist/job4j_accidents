package ru.job4j.accidents.service;

import lombok.AllArgsConstructor;
import org.apache.http.annotation.ThreadSafe;
import org.springframework.stereotype.Service;
import ru.job4j.accidents.model.Rule;
import ru.job4j.accidents.repository.RuleRepository;

import java.util.List;
import java.util.Optional;

@Service
@ThreadSafe
@AllArgsConstructor
public class MemRuleService implements RuleService {
    private final RuleRepository ruleRepository;

    @Override
    public Optional<Rule> findById(int id) {
        return ruleRepository.findById(id);
    }

    @Override
    public List<Rule> findAll() {
        return ruleRepository.findAll();
    }
}
