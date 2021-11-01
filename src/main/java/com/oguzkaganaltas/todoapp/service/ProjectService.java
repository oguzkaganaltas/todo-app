package com.oguzkaganaltas.todoapp.service;

import com.oguzkaganaltas.todoapp.model.Project;
import com.oguzkaganaltas.todoapp.repository.ProjectRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ProjectService {
    private final ProjectRepository projectRepository;

    public List<Project> getAllProjects(){
        return projectRepository.findAll();
    }

    public Project createProject(Project newProject){
        return projectRepository.save(newProject);
    }

    public void deleteProject(int projectId, long ownerId){
        projectRepository.deleteProjectById(projectId,ownerId);
    }

    public Project getProjectById(int id){
        return projectRepository.findById(id)
                .orElseThrow(()->new RuntimeException("Project not found"));
    }

    public List<Project> getProjectsByOwnerId(long ownerId){
        List<Project> projects = projectRepository.findProjectsByOwnerId(ownerId);
        if (projects.isEmpty()) {
            throw new RuntimeException("no projects found");
        }
        return projects;
    }

    public Project getProjectByOwnerId(long ownerId, int projectId){
        Project project = projectRepository.findProjectByOwnerId(ownerId,projectId);
        if (project == null) {
            throw new RuntimeException("no projects found");
        }
        return project;
    }

}
