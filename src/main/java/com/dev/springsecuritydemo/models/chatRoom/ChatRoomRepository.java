package com.dev.springsecuritydemo.models.chatRoom;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Integer> {
    @Query("SELECT c FROM ChatRoom c JOIN c.users u1 JOIN c.users u2 WHERE u1.id = :senderId AND u2.id = :receiverId")
    ChatRoom findChatRoomByUsers(@Param("senderId") Integer senderId, @Param("receiverId") Integer receiverId);

    List<ChatRoom> findByUsers_Id(Integer userId);
}
