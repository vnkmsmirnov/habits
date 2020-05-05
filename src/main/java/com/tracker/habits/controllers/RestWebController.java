package com.tracker.habits.controllers;

import com.tracker.habits.entities.User;
import com.tracker.habits.services.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;

@RestController
public class RestWebController {

    private com.tracker.habits.repositories.UserRepo userRepo;
    private UserServiceImpl userServiceImpl;

    @Autowired
    public void setUserServiceImpl(UserServiceImpl userServiceImpl) {
        this.userServiceImpl = userServiceImpl;
    }

    @Autowired
    public void setUserRepo(com.tracker.habits.repositories.UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView index(@RequestParam(defaultValue = "All habits") String habit, Principal principal, Model model) {

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("index");


        if (principal != null) {
            User user = userRepo.findOneByUsername(principal.getName());
            model.addAttribute("username", user.getUsername());
        }

        return modelAndView;
    }

    @GetMapping(value = "/api/event/all")
    public String getEvents(
            @RequestParam(value = "selected", defaultValue = "All habits") String type,
            Principal principal) {

        return userServiceImpl.findHabitsByUser(principal.getName(), type);
    }

    @GetMapping(value = "/api/event/add")
    public String addEvent(
            @RequestParam(value = "selected", defaultValue = "All habits") String selected,
            @RequestParam(value = "type", required = false) String type,
            @RequestParam(value = "start", required = false) String start,
            @RequestParam(value = "end", required = false) String end,
            Principal principal) {

        return userServiceImpl.addEvent(selected, type, start, end, principal);
    }

    @DeleteMapping(value = "/api/event/delete/{id}")
    public String deleteEvent(
            @RequestParam(value = "selected", defaultValue = "All habits") String selected,
            @PathVariable("id") Long id,
                            Principal principal) {
        return userServiceImpl.deleteEvent(selected, id, principal);
    }
}