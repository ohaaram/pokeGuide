package com.pokeguide.controller.chat;

import com.pokeguide.dto.ChatMessageDTO;
import com.pokeguide.entity.ChatMessage;
import com.pokeguide.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ChatContoller {

    private final ChatService chatService;

    @PostMapping("/messages")
    public ChatMessage sendMessage(@RequestBody ChatMessageDTO messageDTO) {
        return chatService.saveChat(messageDTO);
    }
}
