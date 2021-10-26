package com.oguzkaganaltas.todoapp.service;

import com.oguzkaganaltas.todoapp.model.Task;
import com.oguzkaganaltas.todoapp.repository.TaskRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;

    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    public Task createTask(Task newTask) {
        return taskRepository.save(newTask);
    }

    public void deleteTask(int id) {
        taskRepository.deleteById(id);
    }

    public Task getTaskById(int id) {
        return taskRepository.findById(id)
                .orElseThrow(() ->new RuntimeException("Task not found"));
    }
}
