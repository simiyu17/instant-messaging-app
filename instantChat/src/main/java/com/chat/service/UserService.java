package com.chat.service;

import com.chat.dto.UserDto;
import com.chat.exception.UserNotFoundException;

import java.util.List;

public interface UserService {

    UserDto saveUser(UserDto userDto);

    UserDto findUserById(Long userId);
    UserDto findUserByUserName(String userName);

    List<UserDto> getAllUsers();

    UserDto markAsConnect(UserDto user) throws UserNotFoundException;

    UserDto markAsDisconnect(UserDto user);
}
