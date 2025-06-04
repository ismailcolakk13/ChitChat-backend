package com.dev.springsecuritydemo.models.chatRoom;

import com.dev.springsecuritydemo.models.message.Message;
import com.dev.springsecuritydemo.models.myUser.MyUser;
import com.dev.springsecuritydemo.models.myUser.MyUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.nio.file.AccessDeniedException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatRoomService {

    private final ChatRoomRepository chatRoomRepository;
    private final MyUserRepository myUserRepository;

    public boolean hasTheyChatRoom(Integer senderId,Integer receiverId){
        ChatRoom chatRoom = chatRoomRepository.findChatRoomByUsers(senderId,receiverId);
        return chatRoom != null;
    }

    public ChatRoom getChatRoomById(Integer roomId) {
        return chatRoomRepository.findById(roomId).orElseThrow(() -> new RuntimeException("Room not found"));
    }

    public ChatRoom createOrGetChatRoom(Integer senderId, Integer receiverId) {
        if(hasTheyChatRoom(senderId,receiverId)){
            return chatRoomRepository.findChatRoomByUsers(senderId,receiverId);
        }
        ChatRoom chatRoom = ChatRoom.builder()
                .users(List.of(
                        myUserRepository.findById(senderId).orElseThrow(() -> new RuntimeException("Sender not found")),
                        myUserRepository.findById(receiverId).orElseThrow(() -> new RuntimeException("Receiver not found"))
                ))
                .build();
        chatRoomRepository.save(chatRoom);
        return chatRoom;
    }

    public List<ChatRoom> getUsersChatRooms() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        MyUser currentUser = myUserRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("User not found"));

        return chatRoomRepository.findByUsers_Id(currentUser.getId());
    }

    public List<Message> getMessagesByChatRoom(Integer roomId) throws AccessDeniedException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        MyUser currentUser = myUserRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("User not found"));

        ChatRoom chatRoom = chatRoomRepository.findById(roomId).orElseThrow(() -> new RuntimeException("Room not found"));

        boolean isMember = chatRoom.getUsers().stream().anyMatch(user -> user.getId().equals(currentUser.getId()));
        if (!isMember) {
            throw new AccessDeniedException("User is not authorized to enter this room");
        }

        return chatRoom.getMessages();
    }

}
