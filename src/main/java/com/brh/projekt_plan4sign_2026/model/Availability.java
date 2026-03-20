package com.brh.projekt_plan4sign_2026.model;


import java.time.LocalDate;
import java.time.LocalTime;

public class Availability {

    // Entity Class
    private int availabilityID;
    private String availabilityType;
    private LocalDate date;
    private LocalTime startTime;
    private LocalTime endTime;
    private LocalDate dateFrom;
    private LocalTime dateTo;
    private String comment;
    private int dolmetscherID;

    // Constructor
    public Availability(int availabilityID, String availabilityType, LocalDate date, LocalTime startTime, LocalTime endTime, LocalDate dateFrom, LocalTime dateTo, String comment, int DolmetscherID) {
        this.availabilityID = availabilityID;
        this.availabilityType = availabilityType;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
        this.comment = comment;
        this.dolmetscherID = DolmetscherID;

    }

    // Getters
    public int getAvailabilityID() { return availabilityID; }
    public String getAvailabilityType() { return availabilityType; }
    public LocalDate getDate() { return date; }
    public LocalTime getStartTime() { return startTime; }
    public LocalTime getEndTime() { return endTime; }
    public LocalDate getDateFrom() { return dateFrom; }
    public LocalTime getDateTo() { return dateTo; }
    public String getComment() { return comment; }
    public int getDolmetscherID() { return dolmetscherID; }

    // Setters
    public void setAvailabilityID(int availabilityID) { this.availabilityID = availabilityID; }
    public void setAvailabilityType(String availabilityType) { this.availabilityType = availabilityType; }
    public void setDate(LocalDate date) { this.date = date; }
    public void setStartTime(LocalTime startTime) { this.startTime = startTime; }
    public void setEndTime(LocalTime endTime) { this.endTime = endTime; }
    public void setDateFrom(LocalDate dateFrom) { this.dateFrom = dateFrom; }
    public void setDateTo(LocalTime dateTo) { this.dateTo = dateTo; }
    public void setComment(String comment) { this.comment = comment; }
    public void setDolmetscherID(int dolmetscherID) { this.dolmetscherID = dolmetscherID; }
}
