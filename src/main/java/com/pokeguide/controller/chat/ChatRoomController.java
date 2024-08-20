package com.pokeguide.controller.chat;

import com.pokeguide.entity.ChatMessage;
import com.pokeguide.entity.ChatRoom;
import com.pokeguide.repository.ChatMessageRepository;
import com.pokeguide.repository.ChatRoomRepository;
import com.pokeguide.service.ChatRoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class ChatRoomController {
    private final ChatRoomRepository chatRoomRepository;
    private final ChatMessageRepository chatMessageRepository;
    private final ChatRoomService chatRoomService;

    @GetMapping("/chatrooms")
    public List<ChatRoom> getChatRooms() {
        return chatRoomRepository.findAll();
    }

    @PostMapping("/chatrooms")
    public ChatRoom createChatRoom(@RequestBody ChatRoom chatRoom) {
        return chatRoomService.createChatRoom(chatRoom.getTitle(), chatRoom.getUid());
    }

    @GetMapping("/messages/chatroom/{chatNo}")
    public ResponseEntity<?> getMessagesByChatRoom(@PathVariable int chatNo, @RequestParam String uid) {
        if (!chatRoomService.isUserAuthorized(chatNo, uid)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Access denied");
        }

        List<ChatMessage> messages = chatMessageRepository.findByChatNo(chatNo);
        if (messages == null) {
            messages = new ArrayList<>();  // 메시지가 없을 경우 빈 배열로 초기화
        }
        return ResponseEntity.ok(messages);
    }





}
