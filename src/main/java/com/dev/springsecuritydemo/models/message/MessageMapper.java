package com.dev.springsecuritydemo.models.message;

import java.util.List;

public class MessageMapper {
    public static MessageDTO toDTO(Message message) {
        return new MessageDTO(message.getSenderId(), message.getReceiverId(), message.getText(), message.getDate(), message.getIsRead(),message.getMessageId());
    }

    public static List<MessageDTO> toDTOList(List<Message> messages) {
        return messages.stream().map(MessageMapper::toDTO).toList();
    }

//    public static Message toEntity(MessageDTO dto) {
//        return Message.builder()
//                .senderId(dto.senderId())
//                .receiverId(dto.receiverId())
//                .text(dto.text())
//                .date(dto.date())
//                .isRead(dto.isRead())
//                .messageId(dto.messageId())
//                .build();
//    }
}
