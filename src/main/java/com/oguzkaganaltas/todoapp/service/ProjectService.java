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

    public void deleteProject(int id){
        projectRepository.deleteById(id);
    }

    public Project getProjectById(int id){
        return projectRepository.findById(id)
                .orElseThrow(()->new RuntimeException("Project not found"));
    }

}
