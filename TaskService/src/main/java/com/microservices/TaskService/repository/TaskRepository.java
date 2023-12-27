package com.microservices.TaskService.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.microservices.TaskService.entity.Task;

@Repository
public interface TaskRepository extends JpaRepository<Task, Integer> {

}