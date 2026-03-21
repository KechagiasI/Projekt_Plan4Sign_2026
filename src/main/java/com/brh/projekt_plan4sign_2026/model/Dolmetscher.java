package com.brh.projekt_plan4sign_2026.model;

public class Dolmetscher {

    // Entity Class
    private int dolmetscherID;
    private String firstname;
    private String lastname;
    private String email;
    private String mobilephone;
    private String comment;
    private int userID;

    // Constructor
    public Dolmetscher(int dolmetscherID,  String firstname, String lastname, String email, String mobilephone, String comment, int userID) {
        this.dolmetscherID = dolmetscherID;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.mobilephone = mobilephone;
        this.comment = comment;
        this.userID = userID;
    }

    // Getters
    public int getDolmetscherID() { return dolmetscherID; }
    public String getFirstname() { return firstname; }
    public String getLastname() { return lastname; }
    public String getEmail() { return email; }
    public String getMobilephone() { return mobilephone; }
    public String getComment() { return comment; }
    public int getUserID() { return userID; }

    // Setters
    public void setDolmetscherID(int dolmetscherID) { this.dolmetscherID = dolmetscherID; }
    public void setFistname(String fistname) { this.firstname = firstname; }
    public void setLastname(String lastname) { this.lastname = lastname; }
    public void setEmail(String email) { this.email = email; }
    public void setMobilephone(String mobilephone) { this.mobilephone = mobilephone; }
    public void setComment(String comment) { this.comment = comment; }
    public void setUserID(int userID) { this.userID = userID; }

}
