package com.oguzkaganaltas.todoapp.repository;

import com.oguzkaganaltas.todoapp.model.Project;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectRepository extends JpaRepository<Project, Integer> {
}