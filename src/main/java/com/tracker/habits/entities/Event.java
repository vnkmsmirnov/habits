package com.tracker.habits.entities;


import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Collection;

@Entity
@Table(name = "events")
public class Event implements Comparable<Event> {
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

    public Event() {

    }

    public Event(String title, Timestamp start, Timestamp end) {
        this.title = title;
        this.start = start;
        this.end = end;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Timestamp getStart() {
        return start;
    }

    public void setStart(Timestamp start) {
        this.start = start;
    }

    public Timestamp getEnd() {
        return end;
    }

    public void setEnd(Timestamp end) {
        this.end = end;
    }

    public int compareTo(Event e){

        return title.compareTo(e.getTitle());
    }
}
