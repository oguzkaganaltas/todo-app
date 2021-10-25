package com.oguzkaganaltas.todoapp.model;

import javax.persistence.Entity;
import java.util.ArrayList;

@Entity
public class User {
    private int id;
    private String username;
    private String password;
    private final ArrayList<Project> projects;

    public User(int id, String username, String password) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.projects = new ArrayList<>();
    }

    public User() {
        this.projects = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
