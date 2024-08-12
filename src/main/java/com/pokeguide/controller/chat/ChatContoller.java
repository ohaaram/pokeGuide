package com.pokeguide.controller.chat;

import com.pokeguide.dto.ChatMessageDTO;
import com.pokeguide.entity.ChatMessage;
import com.pokeguide.entity.User;
import com.pokeguide.service.ChatService;
import com.pokeguide.service.SocketIOService;
import com.pokeguide.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.corundumstudio.socketio.SocketIOServer;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.*;

@RestController
@RequiredArgsConstructor
public class ChatContoller {

    private final ChatService chatService;
    private final UserService userService;
    private final SocketIOService socketIOService; // Socket.IO 서버 주입


    @PostMapping("/messages")
    public ChatMessage sendMessage(@RequestBody ChatMessageDTO messageDTO) {
        ChatMessage savedMessage = chatService.saveChat(messageDTO);

        // 클라이언트에 메시지 전송
        socketIOService.sendMessage("chat message", savedMessage);

        return savedMessage;
    }


    @PostMapping("/invite")
    public ResponseEntity<Map<String, Object>> inviteUser(@RequestBody Map<String, String> request) {
        String chatNo = request.get("chatNo");
        String inviteUid = request.get("inviteUid");

        // Check if the user exists
        Optional<User> user = userService.findByUid(inviteUid);
        if (user.isPresent()) {
            // Add the user to the chat room
            chatService.addUserToChatRoom(Integer.parseInt(chatNo), inviteUid);

            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            return ResponseEntity.ok(response);
        } else {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "User not found.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    @PostMapping("/upload")
    public ResponseEntity<?> handleFileUpload(@RequestParam("file") MultipartFile file,
                                              @RequestParam("uid") String uid,
                                              @RequestParam("chatNo") int chatNo) {
        try {
            // 저장할 경로 설정
            String fileName = file.getOriginalFilename();
            Path uploadPath = Paths.get("uploads/");


            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);  // 경로가 없으면 생성
            }

            // 파일 저장
            Path filePath = uploadPath.resolve(fileName);
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);


            // 파일의 URL 생성
            String fileUrl = "/uploads/" + URLEncoder.encode(fileName, "UTF-8").replace("+", "%20");
            return ResponseEntity.ok(Collections.singletonMap("imageUrl", fileUrl));
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}