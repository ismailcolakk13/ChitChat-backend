package com.dev.springsecuritydemo.controllers;

import com.dev.springsecuritydemo.models.message.MessageDTO;
import com.dev.springsecuritydemo.models.message.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class ChatWebSocketController {

    private final SimpMessagingTemplate messagingTemplate;
    private final MessageService messageService;

    @MessageMapping("/chat.send/{roomId}")
    public void onMessage(@DestinationVariable Integer roomId, MessageDTO incomingMessage) {
        messageService.sendMessageToRoom(roomId,incomingMessage.senderId(),incomingMessage.text());
    }

    @MessageMapping("/chat.read/{roomId}")
    public void onReadMessage(@DestinationVariable Integer roomId, MessageDTO incomingMessage) {
        messageService.readMessage(incomingMessage.messageId());

        messagingTemplate.convertAndSend("/topic/rooms/" + roomId + "/read-receipt", incomingMessage);
    }

}
