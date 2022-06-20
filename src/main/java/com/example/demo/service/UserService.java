package com.example.demo.service;

import com.example.demo.domain.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {
    public List<User> getAll();
    public User getByLogin(String login);
    User save(User user);

}
