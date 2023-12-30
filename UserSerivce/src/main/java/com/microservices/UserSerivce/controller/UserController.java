package com.microservices.UserSerivce.controller;

import com.microservices.UserSerivce.RequestResponse.LoginRequest;
import com.microservices.UserSerivce.RequestResponse.Response;
import com.microservices.UserSerivce.dto.TaskDto;
import com.microservices.UserSerivce.dto.UserDto;
import com.microservices.UserSerivce.dto.UserResponse;
import com.microservices.UserSerivce.entity.User;
import com.microservices.UserSerivce.exception.CustomNotFoundException;
import com.microservices.UserSerivce.jwt.JwtUtils;
import com.microservices.UserSerivce.mapper.UserMapper;
import com.microservices.UserSerivce.repository.UserRepository;
import com.microservices.UserSerivce.service.UserDetailsCustom;
import com.microservices.UserSerivce.service.UserService;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserDetailsCustom userDetails;
    @Autowired
    private PasswordEncoder encoder;
    @Autowired
    private JwtUtils jwtUtils;
    @Autowired
    private UserRepository repository;

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
    public ResponseEntity<UserResponse> loadUser(@PathVariable String username) {
        UserResponse userResponse = userMapper.ReponseToEntity(userService.loadUserByUsername(username));
        userResponse.setTasks(userService
                .getTasksForUser(userMapper.ReponseToEntity(userService.loadUserByUsername(username)).getUsername()));
        return ResponseEntity.ok().body(userResponse);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid LoginRequest loginRequest) {
        User user = userDetails.loadUserByUsername(loginRequest.getUsername());
        if (encoder.matches(loginRequest.getPassword(), user.getPassword())) {
            UserResponse userResponse = userMapper.ReponseToEntity(user);
            userResponse.setTasks(userService.getTasksForUser(user.getUsername()));
            return ResponseEntity.ok()
                    .body(new Response(userResponse, jwtUtils.generateToken(user.getUsername(), "USER"),
                            jwtUtils.generateRefreshToken(user.getUsername())));
        }
        return ResponseEntity.badRequest().body("Username or Password invalide check them please");
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody @Valid UserDto dto) {
        Optional<User> user = repository.findByUsernameOrEmail(dto.getUsername(), dto.getEmail());
        if (user.isPresent()) {
            throw new EntityNotFoundException("founded");
        }
        User newUser = User.builder()
                .nom(dto.getNom())
                .prenom(dto.getPrenom())
                .password(encoder.encode(dto.getPassword()))
                .username(dto.getUsername())
                .email(dto.getEmail()).build();
        userService.add(newUser);

        return ResponseEntity.ok(newUser);
    }

    @DeleteMapping("/{username}/deletetask/{taskId}")
    public ResponseEntity<String> deleteTaskFromUser(@PathVariable String username, @PathVariable int taskId) {
        userService.deleteUserTask(username, taskId);
        return ResponseEntity.ok("Task deleted from user's list and database");
    }

    @PutMapping("/{username}/updatetask/{taskId}")
    public ResponseEntity<TaskDto> editUserTask(
            @PathVariable String username,
            @PathVariable int taskId,
            @RequestBody @Valid TaskDto editedTask) {
        return ResponseEntity.ok(userService.editUserTask(username, taskId, editedTask));
    }
}
