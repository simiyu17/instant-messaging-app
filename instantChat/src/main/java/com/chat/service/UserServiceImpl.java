package com.chat.service;

import com.chat.domain.User;
import com.chat.domain.UserRepositoryWrapper;
import com.chat.dto.UserDto;
import com.chat.exception.DuplicateUserNameException;
import com.chat.exception.UserNotFoundException;
import com.chat.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.PersistenceException;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

    private final UserRepositoryWrapper userRepository;
    private final UserMapper userMapper;

    @Transactional
    @Override
    public UserDto saveUser(UserDto userDto) {
        var existingUser = this.userRepository.getUserOrNullByUserName(userDto.getUserName());
        if (Objects.nonNull(existingUser)){
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
        User dbUser = userRepository.getUserByUserName(user.getUserName());
            dbUser.setConnected(true);
            return userMapper.fromEntity(dbUser);
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
