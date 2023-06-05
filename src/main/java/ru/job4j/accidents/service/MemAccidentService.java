package ru.job4j.accidents.service;

import lombok.AllArgsConstructor;
import org.apache.http.annotation.ThreadSafe;
import org.springframework.stereotype.Service;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.model.Rule;
import ru.job4j.accidents.repository.AccidentRepository;
import ru.job4j.accidents.repository.AccidentTypeRepository;
import ru.job4j.accidents.repository.RuleRepository;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@ThreadSafe
@AllArgsConstructor
public class MemAccidentService implements AccidentService {

    private final AccidentRepository accidentRepository;
    private final AccidentTypeRepository accidentTypeRepository;
    private final RuleRepository ruleRepository;

    @Override
    public Optional<Accident> save(Accident accident, int typeId, Set<Integer> rIds) {
        Set<Rule> rules = rIds.stream().map(ruleRepository::findById)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toSet());
        accident.setType(accidentTypeRepository.findById(typeId).get());
        accident.setRules(rules);
        return Optional.of(accidentRepository.save(accident));
    }

    @Override
    public boolean deleteById(int id) {
        return accidentRepository.deleteById(id);
    }

    @Override
    public boolean update(Accident accident, Set<Integer> rIds) {
        var type = accidentTypeRepository.findById(accident.getType().getId()).get();
        accident.setType(type);
        Set<Rule> rules = rIds.stream().map(ruleRepository::findById)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toSet());
        accident.setRules(rules);
        return accidentRepository.update(accident);
    }

    @Override
    public Optional<Accident> findById(int id) {
        return accidentRepository.findById(id);
    }

    @Override
    public List<Accident> findAll() {
        return accidentRepository.findAll();
    }
}
