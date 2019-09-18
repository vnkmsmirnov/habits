package com.tracker.habits.controllers;

import com.tracker.habits.entities.Event;
import com.tracker.habits.entities.User;
import com.tracker.habits.services.HabitsService;
import com.tracker.habits.services.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.security.Principal;
import java.util.Optional;

@Controller
public class CalendarController {

    private UserRepository userRepository;

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Autowired
    HabitsService habitsService;

    @RequestMapping(path = "/", method = RequestMethod.GET)
    String index(Principal principal, Model model) {

        Long id = 1l;

        if (principal != null) {
            User user = userRepository.findByUsername(principal.getName());
            model.addAttribute("username", user.getUsername());
        }

        Optional<Event> event = habitsService.findById(id);
        Event allHabits = event.get();


        model.addAttribute("habits", allHabits);
        return "index";
    }
}
