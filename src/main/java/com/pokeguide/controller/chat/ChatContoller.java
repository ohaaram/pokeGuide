package com.pokeguide.controller.chat;

import com.pokeguide.dto.ChatMessageDTO;
import com.pokeguide.entity.ChatMessage;
import com.pokeguide.entity.User;
import com.pokeguide.service.ChatService;
import com.pokeguide.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class ChatContoller {

    private final ChatService chatService;
    private final UserService userService;

    @PostMapping("/messages")
    public ChatMessage sendMessage(@RequestBody ChatMessageDTO messageDTO) {
        return chatService.saveChat(messageDTO);
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

    @GetMapping("/messages/{chatNo}/chatroom")
    public List<ChatMessage> getMessagesByChatNo(@PathVariable int chatNo) {
        return chatService.getMessagesByChatNo(chatNo);
    }

}
