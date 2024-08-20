package com.pokeguide.controller.chat;

import com.pokeguide.dto.ChatMessageDTO;
import com.pokeguide.entity.ChatFile;
import com.pokeguide.entity.ChatMessage;
import com.pokeguide.entity.User;
import com.pokeguide.repository.ChatFileRepository;
import com.pokeguide.service.ChatService;
import com.pokeguide.service.SocketIOService;
import com.pokeguide.service.UserService;
import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.corundumstudio.socketio.SocketIOServer;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.util.*;

@RestController
@RequiredArgsConstructor
public class ChatContoller {

    private final ChatService chatService;
    private final UserService userService;
    private final SocketIOService socketIOService; // Socket.IO 서버 주입
    private final ChatFileRepository chatFileRepository;


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
            String originalFilename = file.getOriginalFilename();
            String uniqueFilename = UUID.randomUUID().toString() + "_" + originalFilename; // 고유한 파일 이름 생성
            String uploadDir = System.getProperty("user.dir") + "/uploads/";
            Path uploadPath = Paths.get(uploadDir);


            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);  // 경로가 없으면 생성
            }

            // 파일 저장
            Path filePath = uploadPath.resolve(uniqueFilename);
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

            // 파일 URL 생성
            String fileUrl = "/pokeguide/uploads/" + URLEncoder.encode(uniqueFilename, "UTF-8").replace("+", "%20");

            // 파일 정보를 DB에 저장
            ChatFile chatFile = ChatFile.builder()
                    .uid(uid)
                    .filename(uniqueFilename)
                    .build();
            chatFileRepository.save(chatFile);

            // 메시지 DB에 저장
            ChatMessageDTO chatMessage = ChatMessageDTO.builder()
                    .uid(uid)
                    .chatNo(chatNo)
                    .imageUrl(fileUrl)  // 이미지 URL 저장
                    .sName(uniqueFilename)  // filename을 sName으로 저장
                    .message("")  // 메시지 필드는 빈 문자열로 설정
                    .cDate(LocalDateTime.now())
                    .build();
            chatService.saveChat(chatMessage);


            return ResponseEntity.ok(Collections.singletonMap("imageUrl", fileUrl));
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


    @GetMapping("/uploads/{filename:.+}")
    public ResponseEntity<?> serveFile(@PathVariable String filename) {
        System.out.println("Request to serve file!!: " + filename);

        try {
            Path filePath = Paths.get("uploads/" + filename);
            UrlResource resource = new UrlResource(filePath.toUri());

            if (resource.exists() && resource.isReadable()) {
                return ResponseEntity.ok()
                        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                        .body(resource);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("파일을 찾을 수 없습니다.");
            }
        } catch (MalformedURLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("파일을 로드하는 중 오류가 발생했습니다.");
        }
    }

}