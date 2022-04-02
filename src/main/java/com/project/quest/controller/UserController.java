package com.project.quest.controller;

import com.project.quest.model.User;
import com.project.quest.request.UserUpdateRequest;
import com.project.quest.response.UserResponse;
import com.project.quest.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }
    @GetMapping
    public ResponseEntity<List<User>> getAllUsers(){
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user){
        return ResponseEntity.ok(userService.createUser(user));
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserResponse> getUserByUserId(@PathVariable Long userId){
        return ResponseEntity.ok(new UserResponse(userService.getUserByUserId(userId)));
    }

    @PutMapping("/{userId}")
    public ResponseEntity<User> updateUser(@PathVariable Long userId, @RequestBody UserUpdateRequest request){
        return ResponseEntity.ok(userService.updateUser(userId,request));
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long userId){
        userService.deleteUser(userId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/activity/{userId}")
    public ResponseEntity<List<Object>> getUserActivity(@PathVariable Long userId){
        return ResponseEntity.ok(userService.getUserActivity(userId));
    }
}
