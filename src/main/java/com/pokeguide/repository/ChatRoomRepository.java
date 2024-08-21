package com.pokeguide.repository;

import com.pokeguide.entity.ChatRoom;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Integer> {
    @Query("SELECT cr FROM ChatRoom cr JOIN ChatUser cu ON cr.chatNo = cu.chatNo WHERE cu.uid = :uid")
    List<ChatRoom> findChatRoomsByUid(@Param("uid") String uid);
}
