package com.tracker.habits.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@Entity
@Table(name = "events")
public class Event {
    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "start")
    private String start;

    @Column(name = "ended")
    private String end;

    public Event(String title, String start, String end) {
        this.title = title;
        this.start = start;
        this.end = end;
    }
}
