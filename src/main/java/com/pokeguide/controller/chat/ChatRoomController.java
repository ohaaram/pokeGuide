package com.pokeguide.controller.chat;

import com.pokeguide.entity.ChatMessage;
import com.pokeguide.entity.ChatRoom;
import com.pokeguide.repository.ChatMessageRepository;
import com.pokeguide.repository.ChatRoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ChatRoomController {
    private final ChatRoomRepository chatRoomRepository;
    private final ChatMessageRepository chatMessageRepository;

    @GetMapping("/chatrooms")
    public List<ChatRoom> getChatRooms() {
        return chatRoomRepository.findAll();
    }

    @PostMapping("/chatrooms")
    public ChatRoom createChatRoom(@RequestBody ChatRoom chatRoom) {
        return chatRoomRepository.save(chatRoom);
    }

    @GetMapping("/messages/chatroom/{chatNo}")
    public List<ChatMessage> getMessagesByChatRoom(@PathVariable int chatNo) {
        return chatMessageRepository.findByChatNo(chatNo);
    }

    @PostMapping("/messages")
    public ChatMessage saveMessage(@RequestBody ChatMessage chatMessage) {
        return chatMessageRepository.save(chatMessage);
    }
}
