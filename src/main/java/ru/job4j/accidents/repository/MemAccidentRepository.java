package ru.job4j.accidents.repository;

import org.apache.http.annotation.ThreadSafe;
import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.model.AccidentType;
import ru.job4j.accidents.model.Rule;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@ThreadSafe
public class MemAccidentRepository implements AccidentRepository {

    private final Map<Integer, Accident> accidents = new ConcurrentHashMap<>();
    private final AtomicInteger nextId = new AtomicInteger(1);


    public MemAccidentRepository() {
        save(new Accident(0, "Велосипед и Хонда", "столкновение на переходе",
                "перекресток улиц Амундсена, Бардина", new AccidentType(3, "Машина и велосипед"),
                Set.of(new Rule(1, "Статья. 1"))));
        save(new Accident(0, "Лада и Форд", "столкновение в правой полосе",
                "проспект Космонавтов д.47", new AccidentType(1, "Две машины"),
                Set.of(new Rule(1, "Статья. 1"), new Rule(2, "Статья. 2"))));
    }

    @Override
    public Accident save(Accident accident) {
        accident.setId(nextId.getAndIncrement());
        accidents.put(accident.getId(), accident);
        return accident;
    }

    @Override
    public boolean deleteById(int id) {
        return accidents.remove(id) != null;
    }

    @Override
    public boolean update(Accident accident) {
        return accidents.computeIfPresent(accident.getId(), (id, oldAccident) -> new Accident(oldAccident.getId(),
                accident.getName(), accident.getText(), accident.getAddress(), accident.getType(), accident.getRules())) != null;
    }

    @Override
    public Optional<Accident> findById(int id) {
        return Optional.ofNullable(accidents.get(id));
    }

    @Override
    public List<Accident> findAll() {
        return accidents.values().stream().toList();
    }
}
