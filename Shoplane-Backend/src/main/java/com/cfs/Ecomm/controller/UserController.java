package com.cfs.Ecomm.controller;

import com.cfs.Ecomm.dto.LoginRequestDto;
import com.cfs.Ecomm.dto.UserResponseDto;
import com.cfs.Ecomm.model.User;
import com.cfs.Ecomm.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@CrossOrigin("*")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody User user)
    {
        return ResponseEntity.ok(userService.registeruser(user));
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@Valid @RequestBody LoginRequestDto loginRequest)
    {
        User user= userService.loginUser(loginRequest.getEmail(),loginRequest.getPassword());

        if (user !=null){

            UserResponseDto response = new UserResponseDto(
                    user.getId(),
                    user.getName(),
                    user.getEmail()
            );
            return ResponseEntity.ok(response);
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid email or password");
    }

    @GetMapping
    public List<User> getAllUsers()
    {
        return userService.getAllUsers();
    }
}
