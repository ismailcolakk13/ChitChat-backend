package com.dev.springsecuritydemo.models.message;

import java.time.LocalDateTime;

public record MessageDTO(Integer senderId, Integer receiverId,String text, LocalDateTime date, Boolean isRead, Long messageId) {
}
