package com.pokeguide.controller.admin;


import com.pokeguide.dto.UserDTO;
import com.pokeguide.entity.User;
import com.pokeguide.service.AdminService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
public class AdminUserList {

    private final AdminService adminService;

    
    //모든 유저 리스트 출력
    @PostMapping("/admin/userList")
    public ResponseEntity<?> userList(){

        log.info("유저 리스트 출력쪽에 들어왔다!");

       List<User> userList= adminService.userList();

        return ResponseEntity.status(HttpStatus.OK).body(userList);

    }

    //모든 유저 삭제
    @GetMapping("/admin/allUserDel")
    public ResponseEntity<?> allUserDel(){

        log.info("모든 유저 삭제쪽에 들어왔다.");

        int result = adminService.allUserDel();

        return ResponseEntity.status(HttpStatus.OK).body(result);
    }


    //유저 권한 변경
    @PostMapping("/admin/changeRole")
    public ResponseEntity<?> changeRole(UserDTO userDTO){

        log.info("role이 들어왔는지 확인 : "+userDTO.getRole());
        log.info("아이디가 들어왔는지 확인 : "+userDTO.getUid());

        adminService.changeRole(userDTO);

        return ResponseEntity.status(HttpStatus.OK).body(1);
        
    }

}
