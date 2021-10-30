package com.oguzkaganaltas.todoapp.controller;

import com.oguzkaganaltas.todoapp.model.Project;
import com.oguzkaganaltas.todoapp.model.Task;
import com.oguzkaganaltas.todoapp.service.ProjectService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("/projects")
@AllArgsConstructor
public class ProjectController {
    private final ProjectService projectService;

    @GetMapping
    public ResponseEntity<List<Project>> getProjects(){
        return new ResponseEntity<>(this.projectService.getAllProjects(), OK);
    }

    @GetMapping("/{project_id}")
    public ResponseEntity<Project> getProject(@PathVariable int project_id){
        return new ResponseEntity<>(this.projectService.getProjectById(project_id),OK);
    }

    @PostMapping()
    public ResponseEntity<Project> createProject(@RequestBody Project newProject){
        return new ResponseEntity<>(this.projectService.createProject(newProject),CREATED);
    }

    @PutMapping("/{id}")//todo: complete
    public ResponseEntity<Void> getTask(@PathVariable int id, @RequestBody Task newProject){
        Project oldProject = getResult(id);
        return new ResponseEntity<>(OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable int id){
        this.projectService.deleteProject(id);
        return new ResponseEntity<>(OK);
    }

    private Project getResult(int id) {
        return this.projectService.getProjectById(id);
    }


}
