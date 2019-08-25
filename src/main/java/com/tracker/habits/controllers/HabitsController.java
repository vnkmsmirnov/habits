package com.tracker.habits.controllers;

import com.tracker.habits.entities.Event;
import com.tracker.habits.entities.User;
import com.tracker.habits.services.HabitsService;
import com.tracker.habits.services.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/habits")
public class HabitsController {

    private UserRepository userRepository;
    private HabitsService habitsService;

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Autowired
    public void setHabitsService(HabitsService habitsService) {
        this.habitsService = habitsService;
    }

    @GetMapping
    public String showHabitsList(Principal principal,
                                 Model model) {

        if (principal != null) {
            User user = userRepository.findByUsername(principal.getName());
            model.addAttribute("username", user.getUsername());
        }

        List<Event> allHabits = habitsService.findAll();

        model.addAttribute("habits", allHabits);

        return "habits";
    }
}
