package com.dev.springsecuritydemo.controllers;

import com.dev.springsecuritydemo.models.message.MessageDTO;
import com.dev.springsecuritydemo.models.message.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class ChatWebSocketController {

    private final MessageService messageService;

    @MessageMapping("/chat.send/{roomId}")
    public void onMessage(@DestinationVariable Integer roomId, MessageDTO incomingMessage) {
        messageService.sendMessageToRoom(roomId,incomingMessage.senderId(),incomingMessage.text());
    }
}
