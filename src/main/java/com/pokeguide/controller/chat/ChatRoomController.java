package com.pokeguide.controller.chat;

import com.pokeguide.entity.ChatMessage;
import com.pokeguide.entity.ChatRoom;
import com.pokeguide.repository.ChatMessageRepository;
import com.pokeguide.repository.ChatRoomRepository;
import com.pokeguide.service.ChatRoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

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
    public List<ChatMessage> getMessagesByChatRoom(@PathVariable int chatNo) {
        return chatMessageRepository.findByChatNo(chatNo);
    }


}
