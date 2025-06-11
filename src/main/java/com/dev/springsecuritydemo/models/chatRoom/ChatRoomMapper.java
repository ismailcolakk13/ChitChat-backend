package com.dev.springsecuritydemo.models.chatRoom;

import com.dev.springsecuritydemo.models.message.MessageDTO;
import com.dev.springsecuritydemo.models.message.MessageMapper;
import com.dev.springsecuritydemo.models.message.MessageRepository;
import com.dev.springsecuritydemo.models.myUser.MyUserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ChatRoomMapper {

    private final MessageRepository messageRepository;
    private final MessageMapper messageMapper;


    public ChatRoomDTO toDTO(ChatRoom chatRoom) {
        MessageDTO lastMessage = null;
        if (chatRoom.getRoomId() != null) {
            var message = messageRepository.findLastMessageByRoomId(chatRoom.getRoomId());
            if (message != null) {
                lastMessage = messageMapper.toDTO(message);
            }
        }

        return new ChatRoomDTO(
                chatRoom.getRoomId(),
                MyUserMapper.toDTOList(chatRoom.getUsers()),
                lastMessage
        );
    }

    public List<ChatRoomDTO> toDTOList(List<ChatRoom> chatRooms) {
        return chatRooms.stream().map(this::toDTO).collect(Collectors.toList());
    }
}
