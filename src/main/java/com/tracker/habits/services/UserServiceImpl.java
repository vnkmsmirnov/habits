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
    private final String allHabits = "All habits";
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

    public String addEvent(String type, String start, String end, Principal principal) {
        String pattern = "dd/MM/yyyy HH:mm";
        int timeZone = 180;

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

        return findHabitsByUser(principal.getName(), allHabits);
    }

   public String findHabitsByUser(String username, String type) {
       Event defaultEvent = new Event();
       defaultEvent.setTitle(allHabits);
       List<Event> result = new ArrayList<>();
       result.add(defaultEvent);
        if (type.equals(allHabits)) {
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

   public String deleteEvent(Long id, Principal principal) {
       if (principal != null) {
           User user = userRepo.findOneByUsername(principal.getName());
           List<Event> list = (List<Event>)user.getHabits();
           Event temp = list.stream().filter(o -> o.getId().equals(id)).findFirst().get();
           user.getHabits().remove(temp);
           userRepo.save(user);
       }
       return findHabitsByUser(principal.getName(), allHabits);
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