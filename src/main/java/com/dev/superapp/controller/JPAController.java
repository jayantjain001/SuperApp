package com.dev.superapp.controller;

import com.dev.superapp.dao.local.UsersRepository;
import com.dev.superapp.model.local.Users;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/jpa")
@RequiredArgsConstructor
public class JPAController {

    private final UsersRepository usersRepository;

    @GetMapping("")
    public Users fetchUser(){
        return usersRepository.findById(1L).orElse(null);
    }
}
