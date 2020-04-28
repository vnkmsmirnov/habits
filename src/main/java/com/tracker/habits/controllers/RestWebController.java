package com.tracker.habits.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tracker.habits.entities.Event;
import com.tracker.habits.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.io.IOException;

@RestController
@RequestMapping("/api/event")
public class RestWebController {

    private com.tracker.habits.repositories.UserRepo userRepo;

    @Autowired
    public void setUserRepo(com.tracker.habits.repositories.UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @GetMapping(value = "/all")
    public String getEvents(Principal principal) {

        return getAllEventsByUserInJson(principal.getName());
    }

    @GetMapping(value = "/add")
    public String addEvents(
            @RequestParam(value = "start", required = false) String start,
            @RequestParam(value = "end", required = false) String end,
            @RequestParam(value = "type", required = false) String type,
            Principal principal) {

        String pattern = "dd/MM/yyyy HH:mm";
        Integer timeZone = 180;

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

        if (principal != null) {
            User user = userRepo.findOneByUsername(principal.getName());
            user.getHabits().add(event);
            userRepo.save(user);
        }

        return getAllEventsByUserInJson(principal.getName());
    }

    private String getAllEventsByUserInJson(String username) {
        ObjectMapper mapper = new ObjectMapper();
        String result = "";
        try {
            result = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(userRepo.findOneByUsername(username).getHabits());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }
}