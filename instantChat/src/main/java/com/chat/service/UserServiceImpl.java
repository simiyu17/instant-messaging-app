package com.chat.service;

import com.chat.domain.User;
import com.chat.domain.UserRepositoryWrapper;
import com.chat.dto.UserDto;
import com.chat.exception.DuplicateUserNameException;
import com.chat.exception.UserNotFoundException;
import com.chat.mapper.UserMapper;
import com.chat.security.CurrentUserDetails;
import com.chat.security.JwtTokenUtil;
import com.chat.security.SecurityConstants;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepositoryWrapper userRepository;
    private final UserMapper userMapper;
    private final AuthenticationManager authenticationManager;
    private final CurrentUserDetails currentUserDetails;

    @Transactional
    @Override
    public UserDto saveUser(UserDto userDto) {
        var existingUser = this.userRepository.getUserOrNullByUserName(userDto.getUserName());
        if (Objects.nonNull(existingUser)) {
            throw new DuplicateUserNameException("Username already in use by another user!!");
        }
        return this.userMapper.fromEntity(this.userRepository.saveNewUser(userDto));
    }


    @Override
    public UserDto findUserById(Long userId) {
        return this.userMapper.fromEntity(this.userRepository.getUserById(userId));
    }

    @Override
    public UserDto findUserByUserName(String userName) {
        return this.userMapper.fromEntity(this.userRepository.getUserByUserName(userName));
    }

    @Override
    public List<UserDto> getAllUsers() {
        return this.userMapper.fromEntity(this.userRepository.getAllUsers());
    }


    @Transactional
    @Override
    public UserDto markAsConnect(UserDto user) throws UserNotFoundException {
        final var userDetails = currentUserDetails.loadUserByUsername(user.getUserName());
        User dbUser = userRepository.getUserByUserName(user.getUserName());
        this.authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userDetails, SecurityConstants.DEFAULT_PASSWORD, userDetails.getAuthorities()));
        var userDto = userMapper.fromEntity(dbUser);
        userDto.setAuthToken(JwtTokenUtil.createToken(dbUser));
        dbUser.setConnected(true);
        return userDto;
    }

    @Transactional
    @Override
    public UserDto markAsDisconnect(UserDto user) {
        if (user == null) {
            return null;
        }
        User dbUser = userRepository.getUserByUserName(user.getUserName());
        if (dbUser == null) {
            return user;
        }

        dbUser.setConnected(false);
        return userMapper.fromEntity(dbUser);
    }

}
