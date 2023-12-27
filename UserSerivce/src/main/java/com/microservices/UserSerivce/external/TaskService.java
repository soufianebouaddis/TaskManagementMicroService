package com.microservices.UserSerivce.external;

import java.util.Optional;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.microservices.UserSerivce.dto.TaskDto;

@FeignClient(name = "TASK-SERVICES/api/task")
public interface TaskService {
    @GetMapping("/{taskId}")
    public Optional<TaskDto> getTaskById(@PathVariable int taskId);
}