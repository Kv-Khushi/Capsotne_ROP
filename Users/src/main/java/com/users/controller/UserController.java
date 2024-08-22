package com.users.controller;

import com.users.entities.User;
import com.users.indto.LoginRequest;
import com.users.indto.UserRequest;
import com.users.outdto.UserResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.users.service.UserService;

import java.util.ArrayList;
import java.util.List;


@RequestMapping("/users")
@RestController

public class UserController {

    @Autowired
    private UserService userService;


    @PostMapping("/addUser")
    public ResponseEntity<UserResponse> addUser(@RequestBody UserRequest userRequest) {
        UserResponse newUser = userService.addUser(userRequest);
        return new ResponseEntity<>(newUser,HttpStatus.CREATED);
    }
    @PostMapping("/loginUser")
    public ResponseEntity<UserResponse> loginUser(@RequestBody LoginRequest loginRequest){
        UserResponse response =userService.authenticateUser(loginRequest);
        return null;
    }

    @GetMapping("/getAllUser")
    public ResponseEntity<List<User>> getAllUser() {
        List<User> list = userService.getAllUserList();
        if (!list.isEmpty()) {
            return ResponseEntity.ok(list);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @DeleteMapping("/deleteUser/{userId}")
    public ResponseEntity<User> deleteUser(@PathVariable("userId") Long userId) {
        boolean isdeleted= userService.deleteUser(userId);
        return  ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }




}
