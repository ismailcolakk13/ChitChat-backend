package com.dev.springsecuritydemo.models.message;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class MessageMapper {

    public MessageDTO toDTO(Message message) {
        return new MessageDTO(
                message.getSenderId(),
                message.getReceiverId(),
                message.getText(),
                message.getDate(),
                message.getIsRead(),
                message.getMessageId()
        );
    }

    public List<MessageDTO> toDTOList(List<Message> messages) {
        return messages.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }
}

