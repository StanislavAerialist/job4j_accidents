package ru.job4j.accidents.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.repository.*;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@AllArgsConstructor
public class SimpleAccidentService implements AccidentService {

    private final JpaAccidentRepository accidentRepository;
    private final JpaAccidentTypeRepository accidentTypeRepository;
    private final JpaRuleRepository ruleRepository;

    @Override
    public Optional<Accident> save(Accident accident, int typeId, Set<Integer> rIds) {
        accident.setType(accidentTypeRepository.findById(typeId).get());
        accident.setRules(ruleRepository.findByIds(rIds));
        return Optional.of(accidentRepository.save(accident));
    }

    @Override
    public boolean deleteById(int id) {
        accidentRepository.deleteById(id);
        return true;
    }

    @Override
    public boolean update(Accident accident, Set<Integer> rIds) {
        accident.setType(accidentTypeRepository.findById(accident.getType().getId()).get());
        accident.setRules(ruleRepository.findByIds(rIds));
        accidentRepository.save(accident);
        return true;
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
