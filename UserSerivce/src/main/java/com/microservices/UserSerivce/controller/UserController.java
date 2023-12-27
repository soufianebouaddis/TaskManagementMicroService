package com.microservices.UserSerivce.controller;

import com.microservices.UserSerivce.dto.TaskDto;
import com.microservices.UserSerivce.dto.UserDto;
import com.microservices.UserSerivce.entity.User;
import com.microservices.UserSerivce.exception.CustomNotFoundException;
import com.microservices.UserSerivce.mapper.UserMapper;
import com.microservices.UserSerivce.service.UserService;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private UserService userService;
    private UserMapper userMapper;

    public UserController(UserService userService, UserMapper userMapper) {
        this.userService = userService;
        this.userMapper = userMapper;
    }

    @GetMapping("/{userId}")
    public ResponseEntity<User> getUserById(@PathVariable int userId) throws CustomNotFoundException {
        return ResponseEntity.ok(userService.findById(userId));
    }

    @GetMapping("/all")
    public ResponseEntity<List<User>> getAllUsers() {
        try {
            List<User> userList = userService.findAll();
            return new ResponseEntity<>(userList, HttpStatus.OK);
        } catch (CustomNotFoundException ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/save")
    public ResponseEntity<User> saveUser(@RequestBody @Valid UserDto userDto) {
        User user = userMapper.mapToEntity(userDto);
        return new ResponseEntity<>(userService.add(user), HttpStatus.CREATED);
    }

    @PutMapping("/update/{userId}")
    public ResponseEntity<?> updateUser(@PathVariable int userId, @RequestBody @Valid User updatedUser) {
        userService.update(userId, updatedUser);
        return new ResponseEntity<>("User updated successfully", HttpStatus.OK);
    }

    @DeleteMapping("/delete/{userId}")
    public ResponseEntity<?> deleteUser(@PathVariable int userId) {
        try {
            userService.delete(userId);
            return new ResponseEntity<>("User deleted successfully", HttpStatus.OK);
        } catch (CustomNotFoundException ex) {
            return new ResponseEntity<>(ex, HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/{username}")
    public ResponseEntity<?> addTask(@PathVariable String username, @RequestBody TaskDto taskDto) {
        return ResponseEntity.ok().body(userService.addtask(username, taskDto));
    }

    @GetMapping("/load/{username}")
    public ResponseEntity<String> loadUserByUsername(@PathVariable String username) {
        return ResponseEntity.ok().body(userService.loadUsername(username));
    }

    @GetMapping("/loaduser/{username}")
    public ResponseEntity<User> loadUser(@PathVariable String username) {
        return ResponseEntity.ok().body(userService.loadUserByUsername(username));
    }
}
