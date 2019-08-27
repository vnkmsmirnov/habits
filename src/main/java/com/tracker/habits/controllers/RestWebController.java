package com.tracker.habits.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tracker.habits.entities.Event;
import com.tracker.habits.services.HabitsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

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
        try {
            List<Event> allHabits = habitsService.findAll();
            for (Event e : allHabits) {
                System.out.println(e.getType() + " " + e.getStart() + " " + e.getEnd());
            }
            ObjectMapper mapper = new ObjectMapper();
            jsonMsg =  mapper.writerWithDefaultPrettyPrinter().writeValueAsString(allHabits);
        } catch (IOException ioex) {
            System.out.println(ioex.getMessage());
        }
        return jsonMsg;
    }

    @GetMapping(value = "/add")
    public String addEvents() {
        System.out.println("In method AddEvents");
        Date date = new Date();
        String jsonMsg = null;
        Event event = new Event();
        event.setType("first event");
        java.sql.Timestamp timestamp = new java.sql.Timestamp(date.getTime());
        event.setStart(timestamp);
        event.setEnd(timestamp);


        System.out.println(event.getStart());
        habitsService.saveOrUpdate(event);
        return "index";
    }
}