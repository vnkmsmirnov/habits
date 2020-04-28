package com.tracker.habits.repositories;

import com.tracker.habits.entities.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends CrudRepository<User, Long> {
    User findOneByUsername(String username);
}