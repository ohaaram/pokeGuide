package com.pokeguide.service;

import com.pokeguide.dto.ChatMessageDTO;
import com.pokeguide.entity.ChatMessage;
import com.pokeguide.entity.ChatUser;
import com.pokeguide.repository.ChatMessageRepository;
import com.pokeguide.repository.ChatUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class ChatService {
    private final ChatMessageRepository chatMessageRepository;
    private final ChatUserRepository chatUserRepository;

    public ChatMessage saveChat(ChatMessageDTO messageDTO) {

        System.out.println("Received messageDTO: " + messageDTO);

        // 메시지 형식에 따라 ChatMessage 객체를 생성하여 저장하는 로직
        if (messageDTO.getMessage() == null || messageDTO.getMessage().trim().isEmpty()) {
            throw new IllegalArgumentException("Message cannot be null or empty");
        }


        ChatMessage chatMessage = ChatMessage.builder()
                    .message(messageDTO.getMessage().trim())
                    .chatNo(messageDTO.getChatNo())
                    .uid(messageDTO.getUid())
                    .oName(messageDTO.getOName())
                    .sName(messageDTO.getSName())
                    .cDate(messageDTO.getCDate() != null ? messageDTO.getCDate() : LocalDateTime.now())
                    .build();

            // 엔티티 저장
            return chatMessageRepository.save(chatMessage);
    }

    public void addUserToChatRoom(int chatNo, String uid) {
        ChatUser chatUser = new ChatUser();
        chatUser.setChatNo(chatNo);
        chatUser.setUid(uid);
        chatUserRepository.save(chatUser);
    }

    public List<ChatMessage> getMessagesByChatNo(int chatNo) {
        return chatMessageRepository.findByChatNo(chatNo);
    }
}
