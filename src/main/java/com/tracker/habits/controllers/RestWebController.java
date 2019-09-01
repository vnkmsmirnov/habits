package com.tracker.habits.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tracker.habits.entities.Event;
import com.tracker.habits.services.HabitsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;
import java.io.IOException;

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
            ObjectMapper mapper = new ObjectMapper();
            jsonMsg =  mapper.writerWithDefaultPrettyPrinter().writeValueAsString(allHabits);
        } catch (IOException ioex) {
            System.out.println(ioex.getMessage());
        }
        return jsonMsg;
    }

    @GetMapping(value = "/add")
    public String addEvents(
                            @RequestParam(value = "start", required = false) String start,
                            @RequestParam(value = "end", required = false) String end,
                            @RequestParam(value = "type", required = false) String type) {

        System.out.println("In method AddEvents");

        String jsonMsg = null;

        System.out.println(start);
        System.out.println(end);
        System.out.println(type);

//        String json = readJsonFromUrl(sURL);
//        System.out.println(json);


//        Date date = new Date();
//        Event event = new Event();
//        event.setType("first event");
//        java.sql.Timestamp timestamp = new java.sql.Timestamp(date.getTime());
//        event.setStart(timestamp);
//        event.setEnd(timestamp);
//        System.out.println(event.getStart());
//        habitsService.saveOrUpdate(event);

        return "index";
    }

}