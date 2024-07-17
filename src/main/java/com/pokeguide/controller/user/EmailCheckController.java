package com.pokeguide.controller.user;



import com.pokeguide.entity.User;
import com.pokeguide.service.EmailCheckService;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@RestController
@ToString
public class EmailCheckController {

    private final EmailCheckService emailCheckService;

    // 회원가입 이메일 중복체크 및 인증코드 전송
    @PostMapping("/user/email-code")
    public ResponseEntity<?> sendEmailCodeForRegistration(@RequestBody Map<String, String> request) {
        String email = request.get("email");
        log.info("회원가입 이메일: " + email);
        if (emailCheckService.isUserExistByEmail(email)) {
            return ResponseEntity.status(400).body("이메일이 이미 사용 중입니다.");
        } else {
            String encryptedCode = emailCheckService.sendEmailCode(email);
            log.info("이메일 코드 전송 완료, encryptedCode: " + encryptedCode);
            return ResponseEntity.ok().body(Map.of("code", encryptedCode, "message", "이메일 코드 전송 완료"));
        }
    }

    // 회원 ID 찾기 이메일 중복체크 및 인증코드 전송
    @PostMapping("/user/email-code-id")
    public ResponseEntity<?> sendEmailCodeForFindId(@RequestBody Map<String, String> request) {
        String email = request.get("email");
        log.info("id찾기 이메일: " + email);
        if (!emailCheckService.isUserExistByEmail(email)) {
            return ResponseEntity.status(400).body("이메일이 등록되어 있지 않습니다.");
        } else {
            String encryptedCode = emailCheckService.sendEmailCode(email);
            log.info("이메일 코드 전송 완료, encryptedCode: " + encryptedCode);
            return ResponseEntity.ok().body(Map.of("code", encryptedCode, "message", "이메일 코드 전송 완료"));
        }
    }

    // 이메일 인증코드 일치한지 확인
    @PostMapping("/user/verify/email-code")
    public ResponseEntity<?> checkEmailCode(@RequestBody Map<String, String> request) {
        String encryptedCode = request.get("code");
        String inputCode = request.get("inputCode");
        log.info("이메일 인증코드 확인 request:" + request);
        log.info("이메일 인증코드 확인 code:" + request.get("code"));
        log.info("이메일 인증코드 확인 inputCode:" + request.get("inputCode"));
        //String decodedInputCode = new String(java.util.Base64.getDecoder().decode(inputCode));
        String decodedServerCode = new String(java.util.Base64.getDecoder().decode(encryptedCode));

        log.info("이메일 인증코드 확인, decodedServerCode: " + decodedServerCode);

        Map<String, Integer> data = new HashMap<>();
        if (decodedServerCode.equals(inputCode)) {
            data.put("result", 0);
            return ResponseEntity.ok().body(data);
        } else {
            data.put("result", 1);
            return ResponseEntity.status(400).body(data);
        }
    }

    // userId 찾기
    @GetMapping("/user/uid")
    public ResponseEntity<?> findUserId(@RequestParam String name, @RequestParam String email) {
        Optional<User> user = emailCheckService.findUserIdByUserNameAndUserEmail(name, email);
        if (user.isPresent()) {
            return ResponseEntity.ok(user.get());
        } else {
            return ResponseEntity.status(404).body("유저가 없습니다.");
        }
    }

    // userPass 수정
    @GetMapping("/user/new-pass")
    public ResponseEntity<?> changePass(@RequestParam String uid, @RequestParam String pass, @RequestParam String email) {
        long result = emailCheckService.updatePw(uid, pass, email);
        if (result > 0) {
            return ResponseEntity.ok("비밀번호가 성공적으로 변경되었습니다.");
        } else {
            return ResponseEntity.status(500).body("비밀번호 변경에 실패했습니다.");
        }
    }
}