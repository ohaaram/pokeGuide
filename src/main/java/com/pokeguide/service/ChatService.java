package com.pokeguide.service;

import com.pokeguide.dto.ChatMessageDTO;
import com.pokeguide.entity.ChatMessage;
import com.pokeguide.entity.ChatUser;
import com.pokeguide.entity.User;
import com.pokeguide.repository.ChatMessageRepository;
import com.pokeguide.repository.ChatRoomRepository;
import com.pokeguide.repository.ChatUserRepository;
import jakarta.transaction.Transactional;
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
    private final SocketIOService socketIOService;
    private final AlarmService alarmService;

    public ChatMessage saveChat(ChatMessageDTO messageDTO) {

        ChatMessage chatMessage = ChatMessage.builder()
                    .message(messageDTO.getMessage().trim())
                    .chatNo(messageDTO.getChatNo())
                    .uid(messageDTO.getUid())
                    .oName(messageDTO.getOName())
                    .sName(messageDTO.getSName())
                    .imageUrl(messageDTO.getImageUrl())
                    .cDate(messageDTO.getCDate() != null ? messageDTO.getCDate() : LocalDateTime.now())
                    .build();

        // 메시지 형식에 따라 ChatMessage 객체를 생성하여 저장하는 로직
        if (chatMessage.getMessage().isEmpty() && (chatMessage.getImageUrl() == null || chatMessage.getImageUrl().trim().isEmpty())) {
            throw new IllegalArgumentException("Message and Image cannot both be null or empty");
        }

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

    @Transactional
    public void createMessageAndSendNotification(String uid, String message, Integer chatNo) {
        // 1. 메시지 생성 및 데이터베이스에 저장
        ChatMessage chatMessage = ChatMessage.builder()
                .uid(uid)
                .message(message)
                .chatNo(chatNo)
                .build();

        chatMessageRepository.save(chatMessage);

        // 2. 알림 생성 및 데이터베이스에 저장
        alarmService.createAlarm(uid, "New message in chat " + chatNo, chatNo);

        // 3. Socket.IO를 통해 실시간으로 알림 전송
        socketIOService.sendNotification(uid, "New message in chat " + chatNo);
    }
}
