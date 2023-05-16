package com.chat.domain;

import com.chat.dto.UserDto;
import com.chat.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserRepositoryWrapper {

    private final UserRepository userRepository;

    @Transactional
    public User saveNewUser(UserDto userDto){
        var user = User.createUser(userDto);
        return this.userRepository.save(user);
    }

    public User getUserById(Long userId){
        return this.userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));
    }

    public User getUserByUserName(String userName){
        return this.userRepository.findByUserName(userName).orElseThrow(UserNotFoundException::new);
    }

    public User getUserOrNullByUserName(String userName){
        return this.userRepository.findByUserName(userName).orElse(null);
    }

    public List<User> getAllUsers(){
        return this.userRepository.findAll();
    }
}
