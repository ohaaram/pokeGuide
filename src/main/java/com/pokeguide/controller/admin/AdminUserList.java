package com.pokeguide.controller.admin;


import com.pokeguide.entity.User;
import com.pokeguide.service.AdminService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
public class AdminUserList {

    private final AdminService adminService;

    @PostMapping("/admin/userList")
    public ResponseEntity<?> userList(){

        log.info("여기까지 들어왔다!");

       List<User> userList= adminService.userList();

        return ResponseEntity.status(HttpStatus.OK).body(userList);

    }

}
