package com.oguzkaganaltas.todoapp.controller;

import com.oguzkaganaltas.todoapp.model.Task;
import com.oguzkaganaltas.todoapp.service.TaskService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/tasks")
@AllArgsConstructor
public class TaskController {

    private final TaskService taskService;

    @GetMapping
    public ResponseEntity<List<Task>> getTasks(){
        return new ResponseEntity<>(this.taskService.getAllTasks(), OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Task> getTask(@PathVariable int id){
        return new ResponseEntity<>(getResult(id), OK);
    }

    @PostMapping
    public ResponseEntity<Task> createTask(@RequestBody Task newTask){
        return new ResponseEntity<>(this.taskService.createTask(newTask), CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> getTask(@PathVariable int id, @RequestBody Task newTask){
        Task oldTask = getResult(id);
        Objects.requireNonNull(oldTask).setTitle(newTask.getTitle());
        oldTask.setNote(newTask.getNote());
        oldTask.setStatus(newTask.isStatus());

        return new ResponseEntity<>(OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable int id){
        this.taskService.deleteTask(id);
        return new ResponseEntity<>(OK);
    }

    private Task getResult(int id) {
        return this.taskService.getTaskById(id);
    }
}
