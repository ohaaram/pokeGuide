package com.pokeguide.service;

import com.pokeguide.entity.ChatRoom;
import com.pokeguide.entity.ChatUser;
import com.pokeguide.repository.ChatRoomRepository;
import com.pokeguide.repository.ChatUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChatRoomService   {
    private final ChatRoomRepository chatRoomRepository;
    private final ChatUserRepository chatUserRepository;

    public ChatRoom createChatRoom(String title, String uid) {
        // 채팅방 생성
        ChatRoom chatRoom = new ChatRoom();
        chatRoom.setTitle(title);
        chatRoom.setUid(uid); // 사용자 ID 설정
        chatRoom.setStatus("active"); // 예시 상태 설정

        ChatRoom savedRoom = chatRoomRepository.save(chatRoom);

        // 채팅 사용자 추가
        ChatUser chatUser = new ChatUser();
        chatUser.setUid(uid);
        chatUser.setChatNo(savedRoom.getChatNo());
        chatUserRepository.save(chatUser);

        return savedRoom;
    }
}
