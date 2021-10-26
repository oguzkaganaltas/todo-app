package com.oguzkaganaltas.todoapp.repository;

import com.oguzkaganaltas.todoapp.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Integer> {
}