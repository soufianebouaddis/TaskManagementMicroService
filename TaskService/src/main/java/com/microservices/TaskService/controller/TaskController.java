package com.microservices.TaskService.controller;

import java.util.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.microservices.TaskService.dto.TaskDto;
import com.microservices.TaskService.entity.Task;
import com.microservices.TaskService.exception.CustomNotFoundException;
import com.microservices.TaskService.mapper.TaskMapper;
import com.microservices.TaskService.service.TaskService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/task")

public class TaskController {

    private TaskService taskService;
    private TaskMapper taskMapper;

    public TaskController(TaskService taskService, TaskMapper taskMapper) {
        this.taskService = taskService;
        this.taskMapper = taskMapper;
    }

    @GetMapping("/{taskId}")
    public ResponseEntity<Task> getUserById(@PathVariable int taskId) throws CustomNotFoundException {
        return ResponseEntity.ok(taskService.findById(taskId));
    }

    @GetMapping("/all")
    public ResponseEntity<List<Task>> getAllUsers() {
        try {
            List<Task> tasks = taskService.findAll();
            return new ResponseEntity<>(tasks, HttpStatus.OK);
        } catch (CustomNotFoundException ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/save")
    public ResponseEntity<Task> saveUser(@RequestBody @Valid TaskDto taskDto) {
        Task task = taskMapper.mapToEntity(taskDto);
        return new ResponseEntity<>(taskService.add(task), HttpStatus.CREATED);
    }

    @PutMapping("/update/{taskId}")
    public ResponseEntity<?> updateUser(@PathVariable int taskId, @RequestBody @Valid Task updatedTask) {
        taskService.update(taskId, updatedTask);
        return new ResponseEntity<>("User updated successfully", HttpStatus.OK);
    }

    @DeleteMapping("/delete/{taskId}")
    public ResponseEntity<?> deleteUser(@PathVariable int taskId) {
        taskService.delete(taskId);
        return new ResponseEntity<>("Task deleted successfully", HttpStatus.OK);
    }
}