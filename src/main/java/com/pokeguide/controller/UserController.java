package com.pokeguide.controller;

import com.pokeguide.entity.User;
import com.pokeguide.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
    @Slf4j
    @RequiredArgsConstructor
    public class UserController {
        private final UserRepository userRepository;

        @GetMapping("/users")
        public ResponseEntity<User> getUser() {

            List<User> userList = userRepository.findAll();

            return ResponseEntity.ok(userList.get(0));
        }
    }