package com.chat.service;

import com.chat.domain.User;
import com.chat.domain.UserRepositoryWrapper;
import com.chat.dto.UserDto;
import com.chat.exception.DuplicateUserNameException;
import com.chat.exception.UserNotFoundException;
import com.chat.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.PersistenceException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

    private final UserRepositoryWrapper userRepository;
    private final UserMapper userMapper;

    @Override
    public UserDto saveUser(UserDto userDto) {
        try {
            return this.userMapper.fromEntity(this.userRepository.saveNewUser(userDto));
        }catch (final JpaSystemException | DataIntegrityViolationException dve) {
            handleException(dve);
        } catch (final PersistenceException dve) {
            Throwable throwable = ExceptionUtils.getRootCause(dve.getCause());
            handleException((Exception) throwable);
        }
        return null;
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
            if (dbUser.isConnected()) {
                throw new UserNotFoundException("This user is already connected: " + dbUser.getUserName());
            }
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
    private void handleException(final Exception ex){
        var message = ex.getMessage();
        if (message.contains("UNIGUE_USERNAME")){
            throw new DuplicateUserNameException("Username already in use by another user!!");
        }
    }
}
