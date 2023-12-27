package com.microservices.UserSerivce.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.microservices.UserSerivce.entity.User;
import com.microservices.UserSerivce.repository.UserRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class UserDetailsCustom implements UserDetailsService {
    @Autowired
    private UserRepository userrepository;

    @Override
    public User loadUserByUsername(String username) throws UsernameNotFoundException {
        return userrepository.findByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException("username not found"));
    }

}