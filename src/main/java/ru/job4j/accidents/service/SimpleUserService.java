package ru.job4j.accidents.service;

import lombok.AllArgsConstructor;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import ru.job4j.accidents.model.User;
import ru.job4j.accidents.repository.JpaUserRepository;

import java.util.Optional;

@Service
@AllArgsConstructor
public class SimpleUserService implements UserService {
    private static final Logger LOG = LogManager.getLogger(SimpleUserService.class.getName());
    private final JpaUserRepository userRepository;

    @Override
    public Optional<User> create(User user) {
        Optional<User> rsl = Optional.empty();
        try {
            userRepository.save(user);
            rsl = Optional.of(user);
        } catch (Exception e) {
            LOG.debug(e.getMessage(), e);
        }
        return rsl;
    }
}