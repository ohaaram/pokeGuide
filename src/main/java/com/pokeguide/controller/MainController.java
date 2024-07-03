package com.pokeguide.controller;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;

@CrossOrigin(origins = "http://localhost:3000")
@Controller
@Slf4j
@RequiredArgsConstructor
public class MainController {


    @GetMapping("/")
    public ResponseEntity<?> main(){

        return ResponseEntity.status(HttpStatus.OK).body("안녕");
    }
}

