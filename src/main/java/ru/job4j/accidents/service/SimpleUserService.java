package ru.job4j.accidents.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.accidents.model.User;
import ru.job4j.accidents.repository.JpaUserRepository;

import java.util.Optional;

@Service
@AllArgsConstructor
public class SimpleUserService implements UserService {
    private final JpaUserRepository userRepository;

    @Override
    public Optional<User> create(User user) {
        userRepository.save(user);
        return Optional.of(user);
    }
}