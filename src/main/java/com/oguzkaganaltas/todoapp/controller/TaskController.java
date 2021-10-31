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

    @GetMapping("/{taskId}")
    public ResponseEntity<Task> getTask(@PathVariable int taskId){
        return new ResponseEntity<>(getResult(taskId), OK);
    }


    private boolean isAnyMatch(Task newTask) {
        return taskService.getAllTasks().stream().anyMatch(obj -> obj.getId() == newTask.getId());
    }

    @PostMapping("/new-task/{projectId}")
    public ResponseEntity<Task> createTask(@PathVariable int projectId,@RequestBody Task newTask){
        if (isAnyMatch(newTask)){
            return new ResponseEntity<>(CONFLICT);
        }
        newTask.setProject(projectService.getProjectById(projectId));
        return new ResponseEntity<>(this.taskService.createTask(newTask), CREATED);
    }

    @PutMapping("/update/{taskId}")
    public ResponseEntity<Task> updateTask(@PathVariable int taskId, @RequestBody Task newTask){
        Task oldTask = getResult(taskId);
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

    private Task getResult(int id) {
        return this.taskService.getTaskById(id);
    }
}
