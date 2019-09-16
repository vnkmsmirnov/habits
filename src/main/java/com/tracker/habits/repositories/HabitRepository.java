package com.tracker.habits.repositories;

import com.tracker.habits.entities.Event;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Component;


@Component
public interface HabitRepository extends CrudRepository<Event, Long>, PagingAndSortingRepository<Event, Long> {
}
