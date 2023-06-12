package ru.job4j.accidents.controller;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import ru.job4j.accidents.model.User;
import ru.job4j.accidents.repository.JpaAuthorityRepository;
import ru.job4j.accidents.service.SimpleUserService;
import ru.job4j.accidents.service.UserService;

@Controller
public class UserController {
    private final PasswordEncoder encoder;
    private final UserService users;
    private final JpaAuthorityRepository authorities;

    public UserController(PasswordEncoder encoder, SimpleUserService users, JpaAuthorityRepository authorities) {
        this.encoder = encoder;
        this.users = users;
        this.authorities = authorities;
    }

    @PostMapping("/reg")
    public String regSave(@ModelAttribute User user, Model model) {
        user.setEnabled(true);
        user.setPassword(encoder.encode(user.getPassword()));
        user.setAuthority(authorities.findByAuthority("ROLE_USER"));
        var savedUser = users.create(user);
        if (savedUser.isEmpty()) {
            model.addAttribute("error", "Пользователь с таким логином уже существует");
            return "users/register";
        }
        return "redirect:/login";
    }

    @GetMapping("/reg")
    public String regPage() {
        return "users/register";
    }
}
