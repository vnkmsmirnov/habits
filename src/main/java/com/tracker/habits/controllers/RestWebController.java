package com.tracker.habits.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tracker.habits.entities.Event;
import com.tracker.habits.services.HabitsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
        String jsonMessage = null;
        try {
            jsonMessage = getAllEventsInJSON();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return jsonMessage;
    }

    @GetMapping(value = "/add")
    public String addEvents(
                            @RequestParam(value = "start", required = false) String start,
                            @RequestParam(value = "end", required = false) String end,
                            @RequestParam(value = "type", required = false) String type) {

        String pattern = "dd/MM/yyyy HH:mm";
        Integer timeZone = 180;
        String jsonMessage = null;

        Event event = new Event();

        if (type != null) {
            if (type.equals("")) {
                event.setTitle("Default");

            } else {
                event.setTitle(type);
            }
        }

        try {
            if (start != null && end != null) {
                if (start.equals("")) {
                    event.setStart(new Timestamp(new Date().getTime()));
                } else {
                    event.setStart(new Timestamp(new SimpleDateFormat(pattern).parse(start + timeZone).getTime()));
                }

                if (end.equals("")) {
                    event.setEnd(new Timestamp(new Date().getTime()));
                } else {
                    event.setEnd(new Timestamp(new SimpleDateFormat(pattern).parse(end + timeZone).getTime()));
                }
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        habitsService.saveOrUpdate(event);

        return getEvents();
    }

    private String getAllEventsInJSON() throws IOException {
        List<Event> allHabits = habitsService.findAll();
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(allHabits);
    }
}