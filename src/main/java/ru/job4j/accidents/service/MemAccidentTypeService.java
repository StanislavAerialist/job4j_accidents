package ru.job4j.accidents.service;

import lombok.AllArgsConstructor;
import org.apache.http.annotation.ThreadSafe;
import org.springframework.stereotype.Service;
import ru.job4j.accidents.model.AccidentType;
import ru.job4j.accidents.repository.AccidentTypeRepository;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class MemAccidentTypeService implements AccidentTypeService {
    private final AccidentTypeRepository accidentTypeRepository;

    @Override
    public Optional<AccidentType> findById(int id) {
        return accidentTypeRepository.findById(id);
    }

    @Override
    public List<AccidentType> findAll() {
        return accidentTypeRepository.findAll();
    }
}
