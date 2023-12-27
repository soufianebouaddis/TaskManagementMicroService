package com.microservices.UserSerivce.mapper;

import com.microservices.UserSerivce.dto.UserDto;
import com.microservices.UserSerivce.dto.UserResponse;
import com.microservices.UserSerivce.entity.User;

public interface MapperInterface<A, B> {

    public User mapToEntity(UserDto userDto);

    public UserDto mapFromEntity(User user);

    public User ReponseToEntity(UserResponse userResponse);

    public UserResponse ReponseToEntity(User user);
}