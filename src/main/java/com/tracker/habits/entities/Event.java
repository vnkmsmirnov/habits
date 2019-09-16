package com.tracker.habits.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;

@Data
@NoArgsConstructor
@Entity
@Table(name = "events")
public class Event {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "type")
    private String title;

    @Column(name = "start")
    private Timestamp start;

    @Column(name = "ended")
    private Timestamp end;


    public Event(String title, Timestamp start, Timestamp end) {
        this.title = title;
        this.start = start;
        this.end = end;
    }
}
