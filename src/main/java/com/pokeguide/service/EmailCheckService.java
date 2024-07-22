package com.pokeguide.service;

import com.pokeguide.repository.Impl.UserRepositoryImpl;
import jakarta.mail.Message;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import com.pokeguide.entity.User;
import com.pokeguide.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import java.util.Base64;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailCheckService {

    private final UserRepository userRepository;
    private final UserRepositoryImpl userRepositoryimpl;
    private final PasswordEncoder passwordEncoder;

    // email 전송
    private final JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String sender;
    // Check if the user exists by email
    public boolean isUserExistByEmail(String email) {
        return userRepository.findByEmail(email).isPresent();
    }

    // Check if the user exists by phone number
    public boolean isUserExistByHp(String hp) {
        return userRepository.findByHp(hp).isPresent();
    }

    // Check if the user exists by user ID
    public boolean isUserExistByUid(String uid) {
        return userRepository.findById(uid).isPresent();
    }

    // 이메일 코드 전송
    public String sendEmailCode(String receiver) {
        log.info("sender: " + sender);

        // 인증코드 생성
        int code = ThreadLocalRandom.current().nextInt(100000, 1000000);
        String codeStr = String.valueOf(code);

        // 인증코드를 Base64로 암호화
        String encryptedCode = Base64.getEncoder().encodeToString(codeStr.getBytes());

        String title = "^pokeGuide^ 인증코드 입니다.";
        String content = "<h1>인증코드는 " + codeStr + " 입니다.</h1>"; // 실제 코드 전송
        log.info("encryptedContent 체크 : " + encryptedCode);

        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            message.setFrom(new InternetAddress(sender, "보내는 사람", "UTF-8"));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(receiver));
            message.setSubject(title);
            message.setContent(content, "text/html;charset=UTF-8"); // 암호화된 코드로 내용 설정

            javaMailSender.send(message);
            log.info("이메일 전송 완료, 인증코드: " + encryptedCode);
        } catch (Exception e) {
            log.error("sendEmailCode: " + e.getMessage());
        }

        return encryptedCode;
    }

    // UserId 찾기
    public Optional<User> findUserIdByUserNameAndUserEmail(String name, String email) {
        return userRepositoryimpl.findUserIdByUserNameAndUserEmail(name, email);
    }

    // userPass 수정
    public long updatePw(String uid, String pass, String email) {
        String encodedPassword = passwordEncoder.encode(pass);
        return userRepositoryimpl.updateUserPwByUserIdAndUserEmail(uid, encodedPassword, email);
    }
}