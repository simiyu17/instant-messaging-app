package com.chat.service;

import com.chat.domain.Chat;
import com.chat.dto.ChatDto;
import com.chat.dto.CheckChatDto;

import java.util.List;

public interface ChatService {

    void saveChat(Chat chat);

    ChatDto createOrReturn(CheckChatDto chatDto);

    ChatDto findChatById(Long chatId);

    Chat findChatByChatName(String chatName);

    List<ChatDto> getAllChats(String username);
}
