package com.tracker.habits.services;

import com.tracker.habits.entities.Event;
import com.tracker.habits.repositories.HabitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HabitsService {
    private HabitRepository habitRepository;

    @Autowired
    public void setHabitRepository(HabitRepository habitRepository) {
        this.habitRepository = habitRepository;
    }

    public List<Event> findAll() {
        return (List<Event>) habitRepository.findAll();
    }

    public void saveAll(List<Event> events) {
        habitRepository.saveAll(events);
    }
}
