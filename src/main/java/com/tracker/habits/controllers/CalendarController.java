package com.tracker.habits.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class CalendarController {

    @RequestMapping(path = "/calendar", method = RequestMethod.GET)
    String index(Model model) {
        return "index";
    }
}
