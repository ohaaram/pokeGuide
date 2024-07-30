package com.pokeguide.controller.user;

import com.pokeguide.dto.UserDTO;
import com.pokeguide.entity.User;
import com.pokeguide.security.MyUserDetails;
import com.pokeguide.security.SecurityUserService;
import com.pokeguide.service.UserService;
import com.pokeguide.util.JWTProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

    @RestController
    @Slf4j
    @RequiredArgsConstructor
    public class UserController {

    private final UserService userService;

    private final AuthenticationManager authenticationManager;
    private final JWTProvider jwtProvider;
    private final PasswordEncoder passwordEncoder;
    private final SecurityUserService securityUserService;

    // 로그인 //
    @PostMapping("/user/login")
    public ResponseEntity<?> login(@RequestBody UserDTO userDTO){

        log.info("login...1 : " + userDTO);

        try {
            // Security 인증 처리
            UsernamePasswordAuthenticationToken authToken
                    = new UsernamePasswordAuthenticationToken(userDTO.getUid(), userDTO.getPass());
            // 사용자 DB 조회
            Authentication authentication = authenticationManager.authenticate(authToken);

            // 인증된 사용자 가져오기
            MyUserDetails userDetails = (MyUserDetails) authentication.getPrincipal();
            User user = userDetails.getUser();

            // 토큰 발급(액세스, 리프레쉬)
            String access  = jwtProvider.createToken(user, 1); // 1일
            String refresh = jwtProvider.createToken(user, 7); // 7일

            // 리프레쉬 토큰 DB 저장

            // 액세스 토큰 클라이언트 전송
            Map<String, Object> map = new HashMap<>();
            map.put("grantType", "Bearer");
            map.put("uid", user.getUid());
            map.put("name", user.getName());
            map.put("nick", user.getNick());
            map.put("role", user.getRole());
            map.put("gender", user.getGender());
            map.put("email", user.getEmail());
            map.put("address", user.getAddress());
            map.put("profile", user.getProfile());
            map.put("accessToken", access);
            map.put("refreshToken", refresh);

            return ResponseEntity.ok().body(map);

        }catch (Exception e){
            log.info("login...3 : " + e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("user not found");
        }
    }



        // 회원가입 //
    @PostMapping("/user/register")
    public Map<String, String> register(@RequestBody UserDTO userDTO){
        String uid = userService.register(userDTO).getUid();
        return Map.of("userid", uid);
    }




    // 사용자 비밀번호 검증 //
    @PostMapping("/user/verify/pass")
    public ResponseEntity<?> userCheck(@RequestBody UserDTO userDTO) {
        log.info("uid: " + userDTO.getUid());
        log.info("pass: " + userDTO.getPass());
        // 사용자 정보 검색
        UserDetails userDetails = securityUserService.loadUserByUsername(userDTO.getUid());
        if(passwordEncoder.matches(userDTO.getPass(), userDetails.getPassword())) {
            log.info("비밀번호 일치");
            return ResponseEntity.ok().body(1);
        }else {
            log.info("비밀번호 불일치 2 :" + userDTO.getPass());
            log.info("비밀번호 3 : " + userDetails.getUsername());
            return ResponseEntity.ok().body(0);
        }
    }
    // 회원정보 수정 //
    @PatchMapping("/user/info")
    public ResponseEntity<?> updateUser(UserDTO userDTO) {

        return userService.updateUserInfo(userDTO);
    }
}