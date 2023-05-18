package com.chat.service;

import com.chat.domain.User;
import com.chat.domain.UserRepositoryWrapper;
import com.chat.dto.UserDto;
import com.chat.mapper.UserMapper;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepositoryWrapper userRepository;

    @Mock
    private UserMapper userMapper;
    @InjectMocks
    private UserServiceImpl userService;

    private static final List<UserDto> userDtos = List.of(new UserDto("John Doe", "john", "jyirijffohn.doemxnbcbv.uiriroed"),
            new UserDto("Jane Doe", "jane", "janhdjsskse.doeutjueje.jfkfkvv"),
            new UserDto("David Doe", "david", "dlfmbvgjave.doepqyetrhf.cmbjgjfkfom"));

    private static final List<User> users = List.of(User.createUser(new UserDto("John Doe", "john", "jyirijffohn.doemxnbcbv.uiriroed"), new BCryptPasswordEncoder()),
            User.createUser(new UserDto("Jane Doe", "jane", "janhdjsskse.doeutjueje.jfkfkvv"), new BCryptPasswordEncoder()),
            User.createUser(new UserDto("David Doe", "david", "dlfmbvgjave.doepqyetrhf.cmbjgjfkfom"), new BCryptPasswordEncoder()));



    @Test
    void findUserById() {
        when(userRepository.getUserById(anyLong())).thenReturn(users.get(0));
        when(userMapper.fromEntity(users.get(0))).thenReturn(userDtos.get(0));
        final var clientDto = userService.findUserById(1L);
        Assertions.assertThat(clientDto).isNotNull().isEqualTo(userDtos.get(0));
    }

    @Test
    void findUserByUserName() {
        when(userRepository.getUserByUserName(anyString())).thenReturn(users.get(0));
        when(userMapper.fromEntity(users.get(0))).thenReturn(userDtos.get(0));
        final var clientDto = userService.findUserByUserName("simiyu");
        Assertions.assertThat(clientDto).isNotNull().isEqualTo(userDtos.get(0));
    }

    @Test
    void getAllUsers() {
        when(userRepository.getAllUsers()).thenReturn(users);
        when(userMapper.fromEntity(users)).thenReturn(userDtos);
        final var clientDtos = userService.getAllUsers();
        Assertions.assertThat(clientDtos).isNotNull().hasSize(3).isInstanceOf(List.class).contains(userDtos.get(0));
    }

}