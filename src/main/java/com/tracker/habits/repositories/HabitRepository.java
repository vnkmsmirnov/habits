package com.tracker.habits.repositories;

import com.tracker.habits.entities.Event;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Component;


@Component
public interface HabitRepository extends JpaSpecificationExecutor<Event>, PagingAndSortingRepository<Event, Long> {

}
