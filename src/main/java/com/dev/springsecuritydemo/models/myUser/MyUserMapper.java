package com.dev.springsecuritydemo.models.myUser;

import java.util.List;

public class MyUserMapper {
    public static MyUserDTO toDTO(MyUser user) {
        return new MyUserDTO(user.getId(),user.getUsername(), user.getAge(),user.getRole());
    }

    public static List<MyUserDTO> toDTOList(List<MyUser> users) {
        return users.stream().map(MyUserMapper::toDTO).toList();
    }

//    public static MyUser toEntity(MyUserDTO dto, String password) {
//        return MyUser.builder()
//                .id(dto.id())
//                .username(dto.username())
//                .age(dto.age())
//                .password(password) // Parola DTO'da olmadığı için dışarıdan alınmalı
//                .role(dto.role())
//                .build();
//    }
}
