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
    private String type;

    @Column(name = "start")
    private Timestamp start;

    @Column(name = "ended")
    private Timestamp end;

    public Event(String type, Timestamp start, Timestamp end) {
        this.type = type;
        this.start = start;
        this.end = end;
    }
}
