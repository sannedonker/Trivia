package com.example.gebruiker.trivia;

import java.io.Serializable;

public class Player implements Serializable {

    private String name, score, time;

    public Player(String name, String score, String time) {
        this.name = name;
        this.score = score;
        this.time = time;
    }

    public String getName() {
        return name;
    }

    public String getScore() {
        return score;
    }

    public String getTime() {
        return time;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
