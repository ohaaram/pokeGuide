package com.pokeguide.repository;

import com.pokeguide.entity.ChatUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ChatUserRepository extends JpaRepository<ChatUser, Long> {
    Optional<ChatUser> findByChatNoAndUid(int chatNo, String uid);
    List<ChatUser> findByChatNo(int chatNo);
}
