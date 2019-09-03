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
        String jsonMsg = null;
        String pattern = "dd/MM/yyyy HH:mm";
        Integer timeZone = 180;
        Event event = new Event();
        event.setType(type);

        Timestamp timestamp;
        try {
            Date dateStart = new SimpleDateFormat(pattern).parse(start + timeZone);
            Date dateEnd = new SimpleDateFormat(pattern).parse(end + timeZone);
            timestamp = new Timestamp(dateStart.getTime());
            event.setStart(timestamp);
            timestamp = new Timestamp(dateEnd.getTime());
            event.setEnd(timestamp);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        habitsService.saveOrUpdate(event);

        return "index";
    }

    private String getAllEventsInJSON() throws IOException {
        String jsonMsg = null;
        List<Event> allHabits = habitsService.findAll();
        ObjectMapper mapper = new ObjectMapper();
        jsonMsg =  mapper.writerWithDefaultPrettyPrinter().writeValueAsString(allHabits);
        return jsonMsg;
    }
}