package com.tracker.habits.services;

import com.tracker.habits.entities.Habit;
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

    public List<Habit> findAll() {
        return (List<Habit>) habitRepository.findAll();
    }
}
