package com.example.demo.service.impl;

import com.example.demo.domain.User;
import com.example.demo.repositories.UserRepositories;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {

    @Autowired
    private UserRepositories userRepositories;

    @Override
    public List<User> getAll() {
        return userRepositories.findAll();
    }

    @Override
    public User getByLogin(String login) {
        return userRepositories.findByLogin(login);
    }




    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        User u = getByLogin(login);
        if (Objects.isNull(u)) {
            throw new UsernameNotFoundException(String.format("User %s is not found", login));
        }
        return new org.springframework.security
                .core.userdetails.User(u.getLogin(), u.getPassword(), true, true, true, true, new HashSet<>());
    }


}
