package com.oguzkaganaltas.todoapp.controller;

import com.oguzkaganaltas.todoapp.model.Task;
import com.oguzkaganaltas.todoapp.service.ProjectService;
import com.oguzkaganaltas.todoapp.service.TaskService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("/tasks")
@AllArgsConstructor
public class TaskController {

    private final TaskService taskService;
    private final ProjectService projectService;
    @GetMapping
    public ResponseEntity<List<Task>> getTasks(){
        return new ResponseEntity<>(this.taskService.getAllTasks(), OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Task> getTask(@PathVariable int id){
        return new ResponseEntity<>(getResult(id), OK);
    }

    @PostMapping("/new-task/{project_id}")
    public ResponseEntity<Task> createTask(@PathVariable int project_id,@RequestBody Task newTask){
        newTask.setProject(projectService.getProjectById(project_id));
        return new ResponseEntity<>(this.taskService.createTask(newTask), CREATED);
    }

    @PutMapping("/{id}")//todo: fix
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
