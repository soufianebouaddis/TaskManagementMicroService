package com.microservices.UserSerivce.mapper;

import com.microservices.UserSerivce.dto.UserDto;
import com.microservices.UserSerivce.dto.UserResponse;
import com.microservices.UserSerivce.entity.User;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class UserMapper implements MapperInterface<User, UserDto> {
    private ModelMapper modelMapper;

    public UserMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public User mapToEntity(UserDto userDto) {
        return modelMapper.map(userDto, User.class);
    }

    @Override
    public UserDto mapFromEntity(User user) {
        return modelMapper.map(user, UserDto.class);
    }

    @Override
    public User ReponseToEntity(UserResponse userResponse) {
        return modelMapper.map(userResponse, User.class);
    }

    @Override
    public UserResponse ReponseToEntity(User user) {
        return modelMapper.map(user, UserResponse.class);
    }
}
