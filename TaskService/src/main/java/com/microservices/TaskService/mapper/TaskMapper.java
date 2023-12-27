package com.microservices.TaskService.mapper;

import org.modelmapper.ModelMapper;

import com.microservices.TaskService.dto.TaskDto;
import com.microservices.TaskService.entity.Task;
import org.springframework.stereotype.Component;

@Component
public class TaskMapper implements MapperInterface<Task, TaskDto> {
    private ModelMapper modelMapper;

    public TaskMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public Task mapToEntity(TaskDto taskDto) {
        return modelMapper.map(taskDto, Task.class);
    }

    @Override
    public TaskDto mapFromEntity(Task task) {
        return modelMapper.map(task, TaskDto.class);
    }
}
