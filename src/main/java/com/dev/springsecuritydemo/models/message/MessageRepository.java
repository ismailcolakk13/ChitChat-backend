package com.dev.springsecuritydemo.models.message;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MessageRepository extends JpaRepository<Message, Long> {

    @Query(value = "SELECT * FROM message WHERE room_id = :roomId ORDER BY message_id DESC LIMIT 1", nativeQuery = true)
    Message findLastMessageByRoomId(@Param("roomId") Integer roomId);


}
