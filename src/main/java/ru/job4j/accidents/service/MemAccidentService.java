package ru.job4j.accidents.service;

import lombok.AllArgsConstructor;
import org.apache.http.annotation.ThreadSafe;
import org.springframework.stereotype.Service;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.repository.AccidentRepository;

import java.util.List;

@Service
@ThreadSafe
@AllArgsConstructor
public class MemAccidentService implements AccidentService {

    private final AccidentRepository accidentRepository;
    @Override
    public List<Accident> findAll() {
        return accidentRepository.findAll();
    }
}
