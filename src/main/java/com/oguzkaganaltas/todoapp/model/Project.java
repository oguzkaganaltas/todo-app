package com.oguzkaganaltas.todoapp.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.ArrayList;

@Entity
public class Project {

    @Id
    @GeneratedValue
    private int id;
    private String name;
    private final ArrayList<Task> todoItems;

    public Project(int id, String name) {
        this.id = id;
        this.name = name;
        this.todoItems = new ArrayList<>();
    }

    public void addItem(Task todoItem){
        this.todoItems.add(todoItem);
    }

    public Task findItemById(int id){
        for (Task item :
                this.todoItems) {
            if (item.getId() == id) {
                return item;
            }
        }
        return null;
    }

    public void removeItem(Task item){
        this.todoItems.remove(item);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
