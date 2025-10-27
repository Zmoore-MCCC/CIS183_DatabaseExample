package com.example.cis183_databaseexample;

public class User
{
    private int id;
    private String fname;
    private String lname;
    private String email;

    //constructor
    public User()
    {
    }

    //overloaded constructor
    public User(int i, String f, String l, String e)
    {
        id = i;
        fname = f;
        lname = l;
        email = e;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
