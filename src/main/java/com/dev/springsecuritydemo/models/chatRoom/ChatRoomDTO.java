package com.dev.springsecuritydemo.models.chatRoom;

import com.dev.springsecuritydemo.models.message.MessageDTO;
import com.dev.springsecuritydemo.models.myUser.MyUserDTO;

import java.util.List;

public record ChatRoomDTO (Integer roomId, List<MyUserDTO> users, MessageDTO lastMessage) {
}
