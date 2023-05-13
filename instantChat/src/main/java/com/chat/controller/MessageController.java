package com.chat.controller;

import com.chat.dto.ChatDto;
import com.chat.dto.ChatMessageDto;
import com.chat.dto.CheckChatDto;
import com.chat.service.ChatMessageService;
import com.chat.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class MessageController {

    private final ChatMessageService chatMessageService;
    private final ChatService chatService;

    @GetMapping(value = "/chats/{chatId}/messages")
    public List<ChatMessageDto> findMessages(@PathVariable("chatId") Long chatId) {
        return chatMessageService.getChatMessages(chatId);
    }

    @GetMapping(value = "/messages/{chatName}")
    public List<ChatMessageDto> findMessages(@PathVariable("chatName") String chatName) {
        return chatMessageService.getChatMessages(chatName);
    }

    @PostMapping(value = "/chats")
    public ResponseEntity<ChatDto> createChat(@RequestBody CheckChatDto chatDto) {
        return new ResponseEntity<>(chatService.createOrReturn(chatDto), HttpStatus.OK);
    }



}
