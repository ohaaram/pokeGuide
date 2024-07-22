package com.pokeguide.repository;

import com.pokeguide.entity.ChatUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatUserRepository extends JpaRepository<ChatUser, String> {
}
