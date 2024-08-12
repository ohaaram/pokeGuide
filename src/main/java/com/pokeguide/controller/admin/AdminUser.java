package com.pokeguide.controller.admin;


import com.pokeguide.dto.PageRequestDTO;
import com.pokeguide.dto.UserDTO;
import com.pokeguide.entity.User;
import com.pokeguide.service.AdminService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@Slf4j
@RequiredArgsConstructor
public class AdminUser {

    private final AdminService adminService;

    
    //모든 유저 리스트 출력
    @PostMapping("/admin/userList")
    public ResponseEntity<?> userList(@RequestBody PageRequestDTO pageRequestDTO){

        log.info("유저 리스트 출력쪽에 들어왔다!");

        log.info("페이지 번호 : "+pageRequestDTO.getPg());

       ResponseEntity<?> userList= adminService.userList(pageRequestDTO);//페이지 네이션 완성하기

        log.info("유저 리스트 컨트롤러  : "+userList.toString());

        return ResponseEntity.status(HttpStatus.OK).body(userList.getBody());

    }



    //엑셀다운용 모든 유저 리스트 출력
    @PostMapping("/admin/allUserList")
    public ResponseEntity<?> allUserList(){

        log.info("엑셀출력용 유저 리스트 출력쪽에 들어왔다!");

        List<User> userList= adminService.allUserList();//페이지 네이션 완성하기

        return ResponseEntity.status(HttpStatus.OK).body(userList);

    }

    //모든 유저 삭제
    @GetMapping("/admin/allUserDel")
    public ResponseEntity<?> allUserDel(){

        log.info("모든 유저 삭제쪽에 들어왔다.");

        int result = adminService.allUserDel();

        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    //하나의 유저 삭제
    @GetMapping("/admin/delUser")
    public ResponseEntity<?> delUser(@RequestParam("uid")String uid){

        log.info("하나의 유저 삭제 - uid : "+uid);

        int result = adminService.delUser(uid);

        return ResponseEntity.status(HttpStatus.OK).body(result);

    }

    //유저 상태 정지로 변경
    @GetMapping("/admin/userStop")
    public ResponseEntity<?> userStop(@RequestParam("uid")String uid){

        log.info("하나의 유저 삭제 - uid : "+uid);

        int result = adminService.userStop(uid);

        return ResponseEntity.status(HttpStatus.OK).body(result);
    }



    //유저 상태 활성화로 변경
    @GetMapping("/admin/userActive")
    public ResponseEntity<?> userActive(@RequestParam("uid")String uid){

        log.info("하나의 유저 삭제 - uid : "+uid);

        int result = adminService.userActive(uid);

        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    //검색
    @PostMapping("/admin/searchKeyword")
    public ResponseEntity<?> searchKeyword(@RequestBody Map<String,String> search){

        String keyword = search.get("keyword");
        String cate = search.get("searchCate");

        log.info(keyword);
        log.info(cate);

        List<User> users= adminService.search(cate,keyword);

        return ResponseEntity.status(HttpStatus.OK).body(users);

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
