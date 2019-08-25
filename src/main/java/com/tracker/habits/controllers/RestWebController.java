package com.tracker.habits.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tracker.habits.entities.Event;
import com.tracker.habits.services.HabitsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@RestController
@RequestMapping("/api/event")
public class RestWebController {

    private HabitsService habitsService;

    @Autowired
    public void setHabitsService(HabitsService habitsService) {
        this.habitsService = habitsService;
    }

    @GetMapping(value = "/all")
    public String getEvents() {
        System.out.println("Inside method getEvents");
        String jsonMsg = null;
        SimpleDateFormat someDate = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        try {

            List<Event> allHabits = habitsService.findAll();
            List<Event> saveEvents = new ArrayList<>();
            Event event = new Event();
            event.setType("first event");
            event.setStart(someDate.parse("2019-08-29"));
            saveEvents.add(event);

            habitsService.saveAll(saveEvents);

            ObjectMapper mapper = new ObjectMapper();
            jsonMsg =  mapper.writerWithDefaultPrettyPrinter().writeValueAsString(allHabits);
        } catch (IOException ioex) {
            System.out.println(ioex.getMessage());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return jsonMsg;
    }

    @GetMapping(value = "/add")
    public String addEvents() {
        System.out.println("In method AssEvents");
        return "index";
    }
}