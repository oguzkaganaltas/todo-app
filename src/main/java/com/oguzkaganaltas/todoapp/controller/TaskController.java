package com.oguzkaganaltas.todoapp.controller;

import com.oguzkaganaltas.todoapp.model.Task;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    private final List<Task> tasks;

    public TaskController() {
        Task task1 = new Task(1,"task1","hello",false);
        Task task2 = new Task(2,"task2","world",false);

        tasks = Arrays.asList(task1,task2);
    }

    @GetMapping
    public ResponseEntity<List<Task>> getTasks(){
        return new ResponseEntity<>(tasks, OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Task> getTask(@PathVariable int id){
        Task result = this.tasks.stream().filter(task -> task.getId() == id)
                .findFirst()
                .orElseThrow(() ->new RuntimeException("Task not found"));
        return new ResponseEntity<>(result, OK);
    }

}
