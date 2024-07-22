package com.pokeguide.service;

import com.pokeguide.entity.ChatMessage;
import com.pokeguide.entity.ChatUser;
import com.pokeguide.repository.ChatMessageRepository;
import com.pokeguide.repository.ChatUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class ChatService {
    private final ChatMessageRepository chatMessageRepository;
    private final ChatUserRepository chatUserRepository;

    public void saveChat(String msg) {
        // 메시지 형식에 따라 ChatMessage 객체를 생성하여 저장하는 로직
        String[] parts = msg.split("\\*");
        String uid = parts[2];
        Optional<ChatUser> chatUserOptional = chatUserRepository.findById(uid);
        if (chatUserOptional.isPresent()) {
            ChatMessage chatMessage = ChatMessage.builder()
                    .message(parts[0])
                    .chatNo(Integer.parseInt(parts[1]))
                    .uid(uid)
                    .cDate(LocalDateTime.now())
                    .build();
            chatMessageRepository.save(chatMessage);
        } else {
            // Handle error: user not found
            System.out.println("User not found: " + uid);
        }
    }
}
