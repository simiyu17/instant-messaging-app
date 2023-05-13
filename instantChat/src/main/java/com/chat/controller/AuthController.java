package com.chat.controller;

import com.chat.dto.UserDto;
import com.chat.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class AuthController {

    private final UserService userService;

    private final SimpMessagingTemplate template;

    @PostMapping
    public ResponseEntity<UserDto> register(@RequestBody UserDto user) {
        return new ResponseEntity<>(userService.saveUser(user), HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<UserDto> login(@RequestBody UserDto user) {
            UserDto connectedUser = userService.markAsConnect(user);
            template.convertAndSend("/topic/login", connectedUser);
        return new ResponseEntity<>(connectedUser, HttpStatus.OK);
    }

    @PostMapping("/logout")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void logout(@RequestBody UserDto user) {
        UserDto disconnectedUser = userService.markAsDisconnect(user);
        template.convertAndSend("/topic/logout", disconnectedUser);
    }

    @GetMapping
    public ResponseEntity<List<UserDto>> findConnectedUsers() {
        return new ResponseEntity<>(userService.getAllUsers(), HttpStatus.OK);
    }


}
