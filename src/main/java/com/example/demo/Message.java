package com.example.demo;

import javax.persistence.*;

@Entity
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String content;
    private String posteddate;
    private String sentby;
    private String username;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="user_username")
    private User user;




    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getPosteddate() {
        return posteddate;
    }

    public void setPosteddate(String posteddate) {
        this.posteddate = posteddate;
    }

    public String getSentby() {
        return sentby;
    }

    public void setSentby(String sentby) {
        this.sentby = sentby;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
