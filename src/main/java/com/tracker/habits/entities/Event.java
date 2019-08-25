package com.tracker.habits.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Data
@NoArgsConstructor
@Entity
@Table(name = "events")
public class Event {
    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;

    @Column(name = "type")
    private String type;

    @Column(name = "start")
    private Date start;

    @Column(name = "ended")
    private Date end;

    public Event(String type, Date start, Date end) {
        this.type = type;
        this.start = start;
        this.end = end;
    }
}
