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
@RequestMapping("/projects")
@AllArgsConstructor
public class ProjectController {
    private final ProjectService projectService;
    private final UserService userService;

    @GetMapping
    public ResponseEntity<List<Project>> getProjects(@RequestHeader("sessionId") String sessionId){
        return new ResponseEntity<>(this.projectService.getProjectsByOwnerId(userService.getUserBySessionId(sessionId).getId()), OK);
    }

    @GetMapping("/{projectId}")
    public ResponseEntity<Project> getProject(@PathVariable int projectId,@RequestHeader("sessionId") String sessionId){
        User user = userService.getUserBySessionId(sessionId);
        Project project = projectService.getProjectByOwnerId(user.getId(),projectId);
        return new ResponseEntity<>(project,OK);
    }

    private boolean isAnyMatch(Project newProject) {
        return projectService.getAllProjects().stream().anyMatch(obj -> obj.getId() == newProject.getId());
    }

    @PostMapping("/new-project")
    public ResponseEntity<Project> createProject(@RequestBody Project newProject, @RequestHeader("sessionId") String sessionId){
        User user = userService.getUserBySessionId(sessionId);
        newProject.setOwnerId(user.getId());
        if (isAnyMatch(newProject)){
            return new ResponseEntity<>(CONFLICT);
        }
        return new ResponseEntity<>(this.projectService.createProject(newProject), CREATED);
    }

    @PutMapping("/update/{projectId}")
    public ResponseEntity<Project> updateProject(@PathVariable int projectId, @RequestBody Project newProject,@RequestHeader("sessionId") String sessionId){
        User user = userService.getUserBySessionId(sessionId);
        Project oldProject = projectService.getProjectByOwnerId(user.getId(), projectId);
        oldProject.setName(newProject.getName());
        return new ResponseEntity<>(projectService.createProject(oldProject),OK);
    }
    @Transactional
    @DeleteMapping("/remove/{projectId}")
    public ResponseEntity<Void> deleteTask(@PathVariable int projectId, @RequestHeader("sessionId") String sessionId){
        User user = userService.getUserBySessionId(sessionId);
        this.projectService.deleteProject(projectId, user.getId());
        return new ResponseEntity<>(OK);
    }

}
