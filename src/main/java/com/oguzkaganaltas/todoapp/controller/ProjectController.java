package com.oguzkaganaltas.todoapp.controller;

import com.oguzkaganaltas.todoapp.model.Project;
import com.oguzkaganaltas.todoapp.model.User;
import com.oguzkaganaltas.todoapp.service.ProjectService;
import com.oguzkaganaltas.todoapp.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("/api/v1/projects")
@AllArgsConstructor
public class ProjectController {
    private final ProjectService projectService;
    private final UserService userService;

    @GetMapping
    public ResponseEntity<List<Project>> getProjects(@RequestHeader("sessionId") String sessionId){
        User user;
        try{
            user = userService.getUserBySessionId(sessionId);
        }catch (RuntimeException exception){
            return new ResponseEntity<>(NOT_ACCEPTABLE);
        }

        List<Project> projects;
        try {
            projects = projectService.getProjectsByOwnerId(user.getId());
        }catch (RuntimeException exception){
            return new ResponseEntity<>(NOT_FOUND);
        }
        return new ResponseEntity<>(projects, OK);
    }

    @GetMapping("/{projectId}")
    public ResponseEntity<Project> getProject(@PathVariable int projectId,@RequestHeader("sessionId") String sessionId){
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
        return new ResponseEntity<>(project,OK);
    }

    @PostMapping("/new-project")
    public ResponseEntity<Project> createProject(@RequestBody Project newProject, @RequestHeader("sessionId") String sessionId){
        User user;
        try {
            user = userService.getUserBySessionId(sessionId);
        }catch (RuntimeException exception){
            return new ResponseEntity<>(NOT_ACCEPTABLE);
        }
        newProject.setOwnerId(user.getId());
        return new ResponseEntity<>(this.projectService.createProject(newProject), CREATED);
    }

    @PutMapping("/update/{projectId}")
    public ResponseEntity<Project> updateProject(@PathVariable int projectId, @RequestBody Project newProject,@RequestHeader("sessionId") String sessionId){
        User user;
        try{
            user = userService.getUserBySessionId(sessionId);
        }catch (RuntimeException exception){
            return new ResponseEntity<>(NOT_ACCEPTABLE);
        }
        Project oldProject;
        try {
            oldProject = projectService.getProjectByOwnerId(user.getId(),projectId);
        }catch (RuntimeException exception){
            return new ResponseEntity<>(NOT_FOUND);
        }
        oldProject.setName(newProject.getName());
        return new ResponseEntity<>(projectService.createProject(oldProject),OK);
    }
    @Transactional
    @DeleteMapping("/remove/{projectId}")
    public ResponseEntity<Void> deleteTask(@PathVariable int projectId, @RequestHeader("sessionId") String sessionId){
        User user;
        try{
            user = userService.getUserBySessionId(sessionId);
        }catch (RuntimeException exception){
            return new ResponseEntity<>(NOT_ACCEPTABLE);
        }
        this.projectService.deleteProject(projectId, user.getId());
        return new ResponseEntity<>(OK);
    }

}
