package com.chat.service;

import com.chat.dto.ChatMessageDto;

import java.util.List;

public interface ChatMessageService {

    void saveChatMessage(ChatMessageDto messageDto, String destination);

    List<ChatMessageDto> getChatMessages(Long chatId);

    List<ChatMessageDto> getChatMessages(String chatName);
}
