package com.chat.security;

import com.chat.domain.UserRepository;
import com.chat.domain.UserRepositoryWrapper;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;

@Service
public class CurrentUserDetails implements UserDetailsService {

    private final UserRepositoryWrapper userRepository;

    public CurrentUserDetails(UserRepositoryWrapper userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional(readOnly = true)
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        final var user = userRepository.getUserByUserName(username);
        if(Objects.isNull(user)){
            return null;
        }
        final var grantedAuthorities = new HashSet<GrantedAuthority>(Collections.singleton(new SimpleGrantedAuthority("USER")));
        return new org.springframework.security.core.userdetails.User(user.getUserName(), user.getPassword(), grantedAuthorities);
    }
}
