package com.descomplicapostodoapi.Adapters;

import com.descomplicapostodoapi.Models.DTOs.User.UserGetDTO;
import com.descomplicapostodoapi.Models.DTOs.User.UserPostPutDTO;
import com.descomplicapostodoapi.Models.Entities.User;

import java.util.List;
import java.util.stream.Collectors;

public class UserAdapter {
    public UserGetDTO parse(User user) {
        UserGetDTO userGetDTO = new UserGetDTO();

        userGetDTO.setId(user.getId());
        userGetDTO.setEmail(user.getEmail());
        userGetDTO.setName(user.getName());

        return userGetDTO;
    }

    public List<UserGetDTO> parse(List<User> users) {
        return users.stream()
                .map(this::parse)
                .collect(Collectors.toList());
    }

    public User parse(UserPostPutDTO userPostPutDTO) {
        User user = new User();

        user.setEmail(userPostPutDTO.getEmail());
        user.setName(userPostPutDTO.getName());
        user.setPassword(userPostPutDTO.getPassword());

        return user;
    }

    public User parse(UserPostPutDTO userPostPutDTO, User user) {
        user.setEmail(userPostPutDTO.getEmail());
        user.setName(userPostPutDTO.getName());
        user.setPassword(userPostPutDTO.getPassword());

        return user;
    }
}
