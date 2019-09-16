package com.tracker.habits.controllers;

import com.tracker.habits.entities.Event;
import com.tracker.habits.services.HabitsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Optional;

@Controller
public class CalendarController {

    @Autowired
    HabitsService habitsService;

    @RequestMapping(path = "/calendar", method = RequestMethod.GET)
    String index(Model model) {
        Long id = 1l;

        Optional<Event> event = habitsService.findById(id);
        Event allHabits = event.get();


        model.addAttribute("habits", allHabits);
        return "index";
    }
}
