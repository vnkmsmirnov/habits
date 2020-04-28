package com.tracker.habits.controllers;

import com.tracker.habits.entities.Event;
import com.tracker.habits.entities.User;
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

    @RequestMapping(path = "/", method = RequestMethod.GET)
    String index(Principal principal, Model model) {

        Event event = null;

        if (principal != null) {
            User user = userRepository.findByUsername(principal.getName());
            model.addAttribute("username", user.getUsername());
            event = user.getHabits().stream().findFirst().get();
        }

        model.addAttribute("habits", event);
        return "index";
    }
}
