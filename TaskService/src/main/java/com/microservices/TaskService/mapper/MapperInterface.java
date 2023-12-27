package com.microservices.TaskService.mapper;

import com.microservices.TaskService.dto.TaskDto;
import com.microservices.TaskService.entity.Task;

public interface MapperInterface<A, B> {

    public Task mapToEntity(TaskDto taskDto);

    public TaskDto mapFromEntity(Task task);
}