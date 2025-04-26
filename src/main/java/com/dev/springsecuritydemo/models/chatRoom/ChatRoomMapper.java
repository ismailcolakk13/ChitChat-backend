package com.dev.springsecuritydemo.models.chatRoom;

import com.dev.springsecuritydemo.models.message.MessageDTO;
import com.dev.springsecuritydemo.models.message.MessageMapper;
import com.dev.springsecuritydemo.models.myUser.MyUserMapper;

import java.util.List;
import java.util.stream.Collectors;

public class ChatRoomMapper {
    public static ChatRoomDTO toDTO(ChatRoom chatRoom) {
        List<MessageDTO> messages = MessageMapper.toDTOList(chatRoom.getMessages());
        System.out.println(messages);
        int lastIndex = messages.size() - 1;
        System.out.println(lastIndex);

        MessageDTO lastMessage = null;
        if (!messages.isEmpty()) {
            lastMessage = messages.get(lastIndex);
            System.out.println(lastMessage);
        }

        return new ChatRoomDTO(
                chatRoom.getRoomId(),
                MyUserMapper.toDTOList(chatRoom.getUsers()),
                lastMessage
        );
    }


    public static List<ChatRoomDTO> toDTOList(List<ChatRoom> chatRooms) {
        return chatRooms.stream().map(ChatRoomMapper::toDTO).collect(Collectors.toList());
    }

//    public static ChatRoom toEntity(ChatRoomDTO dto) {
//        return ChatRoom.builder()
//                .roomId(dto.roomId())
//                .users(dto.users())
//                .messages(dto.messages())
//                .build();
//    }
}
