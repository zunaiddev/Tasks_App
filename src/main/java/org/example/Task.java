package org.example;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@Entity
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Sr")
    private int id;

    @Transient
    private int serialNo;

    @Column(name = "Title")
    private String title;

    @Column(name = "Status")
    private Boolean isCompleted;

    @Column(name = "Date")
    private LocalDate date;

    @Column(name = "Time")
    private LocalTime time;

    public Task(String title) {
        this();
        this.title = Messages.capitalise(title);
    }

    public Task() {
        this.isCompleted = false;
        this.date = LocalDate.now();
        this.time = LocalTime.now();
    }

    public int getId() {
        return this.id;
    }

    public void updateCompleted(Boolean completed) {
        this.isCompleted = completed;
        this.updateDateAndTime();
    }

    public String getTitle() {
        return title;
    }

    public void updateTitle(String title) {
        this.title = Messages.capitalise(title);
        this.updateDateAndTime();
    }

    public Boolean isCompleted() {
        return isCompleted;
    }

    public String getDate() {
        return this.date.format(DateTimeFormatter.ofPattern("dd MMM yyyy"));
    }

    private void updateDateAndTime() {
        this.date = LocalDate.now();
        this.time = LocalTime.now();
    }

    public int getSerialNo() {
        return serialNo;
    }

    public void setSerialNo(int sr) {
        this.serialNo = sr;
    }

    public String getTime() {
        return this.time.format(DateTimeFormatter.ofPattern("hh:mm"));
    }


    @Override
    public String toString() {
        return "\nTask- " + this.serialNo +
                "\nId: " + getId() +
                "\ntitle: " + getTitle() +
                "\nisCompleted: " + isCompleted() +
                "\nDate: " + getDate() +
                "\ntime: " + getTime();
    }
}
