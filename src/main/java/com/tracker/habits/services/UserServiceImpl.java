package com.tracker.habits.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tracker.habits.entities.Event;
import com.tracker.habits.entities.Role;
import com.tracker.habits.entities.User;
import com.tracker.habits.repositories.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.IOException;
import java.security.Principal;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class UserServiceImpl implements UserRepository {
    private static final String ALL_HABITS = "All habits";
    private static final String DATE_FORMAT = "dd/MM/yyyy HH:mm";
    private static int START_TIME_ZONE = 180;
    private static int END_TIME_ZONE = 240;

    private UserRepo userRepo;

    @Autowired
    public void setUserRepository(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    @Transactional
    public User findByUsername(String username) {
        return userRepo.findOneByUsername(username);
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepo.findOneByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("Invalid username of password");
        }
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
                mapRolesToAuthorities(user.getRoles()));
    }

    public String addEvent(String selected, String type, String start, String end, Principal principal) {
        Event event = new Event();

        if (principal != null) {
            User user = userRepo.findOneByUsername(principal.getName());
            user.getHabits().add(updateEvent(event, type, start, end));
            userRepo.save(user);
        }

        return findHabitsByUser(principal.getName(), selected);
    }

    public String updateEvent(String selected, Long id, String type, String start, String end, Principal principal) {
        if (principal != null) {
            User user = userRepo.findOneByUsername(principal.getName());
            List<Event> eventsList = (List<Event>)user.getHabits();
            Event result = getEventById(eventsList, id);
            updateEvent(result, type, start, end);
            user.setHabits(eventsList);
            userRepo.save(user);
        } else {
            throw new UsernameNotFoundException("Invalid username of password");
        }
        return findHabitsByUser(principal.getName(), selected);
    }

    private Event getEventById(List<Event> eventsList, Long id) {
        for (Event event : eventsList) {
            if (event.getId().equals(id)) {
                return event;
            }
        }
        return new Event();
    }

   public String findHabitsByUser(String username, String type) {
       Event defaultEvent = new Event();
       defaultEvent.setTitle(ALL_HABITS);
       List<Event> result = new ArrayList<>();
       result.add(defaultEvent);
        if (type.equals(ALL_HABITS)) {
            result.addAll(userRepo.findOneByUsername(username).getHabits());
        } else {
            result.addAll(
                    userRepo.findOneByUsername(username).getHabits()
                            .stream()
                            .filter(h -> h.getTitle().equals(type))
                            .collect(Collectors.toList())
            );
        }

        return convertListToJson(result);
   }

   public String deleteEvent(String selected, Long id, Principal principal) {
       if (principal != null) {
           User user = userRepo.findOneByUsername(principal.getName());
           List<Event> list = (List<Event>)user.getHabits();
           Event temp = list.stream().filter(o -> o.getId().equals(id)).findFirst().get();
           user.getHabits().remove(temp);
           userRepo.save(user);
       }
       return findHabitsByUser(principal.getName(), selected);
   }

   private Event updateEvent(Event event, String type, String start, String end) {
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
                   event.setStart(new Timestamp(new SimpleDateFormat(DATE_FORMAT).parse(start + START_TIME_ZONE).getTime()));
               }

               if (end.equals("")) {
                   event.setEnd(new Timestamp(new Date().getTime()));
               } else {
                   event.setEnd(new Timestamp(new SimpleDateFormat(DATE_FORMAT).parse(end + END_TIME_ZONE).getTime()));
               }
           }
       } catch (ParseException e) {
           e.printStackTrace();
       }
       return event;
   }

   private String convertListToJson(List<Event> events) {
       ObjectMapper mapper = new ObjectMapper();
       String result = "";
       try {
           result = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(events);
       } catch (IOException e) {
           e.printStackTrace();
       }
       return result;
   }

    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
    }
}