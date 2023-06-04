package ru.job4j.accidents.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.service.AccidentService;

@Controller
@AllArgsConstructor
public class AccidentController {
    private final AccidentService accidents;

    @GetMapping("/addAccident")
    public String viewCreateAccident() {
        return "accidents/createAccident";
    }

    @PostMapping("/saveAccident")
    public String save(@ModelAttribute Accident accident) {
        accidents.save(accident);
        return "redirect:/";
    }

    @GetMapping("/formUpdateAccident")
    public String update(@RequestParam("id") int id, Model model) {
        model.addAttribute("accident", accidents.findById(id).get());
        return "accidents/editAccident";
    }

    @PostMapping("/update")
    public String update(@ModelAttribute Accident accident, Model model) {
        var isUpdated = accidents.update(accident);
        if (!isUpdated) {
            model.addAttribute("message", "Ошибка обновления инцидента");
            return "errors/404";
        }
        return "redirect:/";
    }
}