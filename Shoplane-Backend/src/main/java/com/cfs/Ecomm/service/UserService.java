package com.cfs.Ecomm.service;

import com.cfs.Ecomm.model.User;
import com.cfs.Ecomm.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public User registeruser(User user)
    {
        if (userRepository.findByEmail(user.getEmail()) !=null){
            throw  new RuntimeException("Email is already registered!");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        User newUser=userRepository.save(user);
        System.out.println("User added Successfully! ");
        return newUser;
    }

    public User loginUser(String email,String password)
    {
        User user=userRepository.findByEmail(email);
        if (user!=null && passwordEncoder.matches(password,user.getPassword()))
        {
            return user;
        }
        return null;
    }

    public List<User> getAllUsers()
    {
        return userRepository.findAll();
    }
}
