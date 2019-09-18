package com.tracker.habits.controllers;

import com.tracker.habits.entities.Event;
import com.tracker.habits.services.HabitsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/habits")
public class HabitsController {

    private HabitsService habitsService;

    @Autowired
    public void setHabitsService(HabitsService habitsService) {
        this.habitsService = habitsService;
    }

    @GetMapping
    public String showHabitsList(Model model) {

        List<Event> allHabits = habitsService.findAll();

        model.addAttribute("habits", allHabits);
        System.out.println("Hello form Habits");

        return "login-page";
    }
}
