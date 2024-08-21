package com.pokeguide.service;

import com.pokeguide.entity.ChatRoom;
import com.pokeguide.entity.ChatUser;
import com.pokeguide.repository.ChatRoomRepository;
import com.pokeguide.repository.ChatUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

        ChatRoom savedRoom = chatRoomRepository.save(chatRoom);

        // 채팅 사용자 추가
        ChatUser chatUser = new ChatUser();
        chatUser.setUid(uid);
        chatUser.setChatNo(savedRoom.getChatNo());
        chatUserRepository.save(chatUser);

        return savedRoom;
    }

    // 사용자를 채팅방에 초대할 때도 ChatUser에 저장
    public void addUserToChatRoom(int chatNo, String uid) {
        ChatUser chatUser = new ChatUser();
        chatUser.setChatNo(chatNo);
        chatUser.setUid(uid);
        chatUserRepository.save(chatUser);
    }

    // 특정 사용자가 속한 채팅방 목록 가져오기
    public List<ChatRoom> findChatRoomsByUid(String uid) {
        return chatRoomRepository.findChatRoomsByUid(uid);
    }

    public boolean isUserAuthorized(int chatNo, String uid) {
        // ChatUser 테이블에서 사용자가 해당 채팅방에 속해 있는지 확인
        Optional<ChatUser> chatUser = chatUserRepository.findByChatNoAndUid(chatNo, uid);
        return chatUser.isPresent();
    }
}
