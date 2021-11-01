package com.oguzkaganaltas.todoapp.controller;

import com.oguzkaganaltas.todoapp.model.Project;
import com.oguzkaganaltas.todoapp.model.Task;
import com.oguzkaganaltas.todoapp.model.User;
import com.oguzkaganaltas.todoapp.service.ProjectService;
import com.oguzkaganaltas.todoapp.service.TaskService;
import com.oguzkaganaltas.todoapp.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("/api/v1/tasks")
@AllArgsConstructor
public class TaskController {

    private final TaskService taskService;
    private final ProjectService projectService;
    private final UserService userService;

    @GetMapping
    public ResponseEntity<List<Task>> getTasks(){
        return new ResponseEntity<>(this.taskService.getAllTasks(), OK);
    }

    @GetMapping("/{taskId}")
    public ResponseEntity<Task> getTask(@PathVariable int taskId, @RequestHeader String sessionId){
        User user;
        try{
            user = userService.getUserBySessionId(sessionId);
        }catch (RuntimeException exception){
            return new ResponseEntity<>(NOT_ACCEPTABLE);
        }
        Task task;
        try {
            task = taskService.getTaskById(taskId);
        }catch (RuntimeException exception){
            return new ResponseEntity<>(NOT_FOUND);
        }
        return new ResponseEntity<>(task, OK);
    }

    @PostMapping("/new-task/{projectId}")
    public ResponseEntity<Task> createTask(@PathVariable int projectId,@RequestBody Task newTask, @RequestHeader String sessionId){

        User user;
        try{
            user = userService.getUserBySessionId(sessionId);
        }catch (RuntimeException exception){
            return new ResponseEntity<>(NOT_ACCEPTABLE);
        }
        Project project;
        try {
            project = projectService.getProjectByOwnerId(user.getId(),projectId);
        }catch (RuntimeException exception){
            return new ResponseEntity<>(NOT_FOUND);
        }
        newTask.setProject(project);
        return new ResponseEntity<>(this.taskService.createTask(newTask), CREATED);
    }

    @PutMapping("/update/{taskId}")
    public ResponseEntity<Task> updateTask(@PathVariable int taskId, @RequestBody Task newTask){
        Task oldTask = taskService.getTaskById(taskId);
        oldTask.setStatus(newTask.isStatus());
        oldTask.setTitle(newTask.getTitle());
        oldTask.setNote(newTask.getNote());
        return new ResponseEntity<>(taskService.createTask(oldTask),OK);
    }

    @DeleteMapping("/remove/{taskId}")
    public ResponseEntity<Void> deleteTask(@PathVariable int taskId){
        this.taskService.deleteTask(taskId);
        return new ResponseEntity<>(OK);
    }

}
