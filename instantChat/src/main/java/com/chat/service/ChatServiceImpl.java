package com.chat.service;

import com.chat.domain.Chat;
import com.chat.domain.ChatMessage;
import com.chat.domain.ChatRepository;
import com.chat.domain.UserRepositoryWrapper;
import com.chat.dto.ChatDto;
import com.chat.dto.ChatMessageDto;
import com.chat.dto.CheckChatDto;
import com.chat.mapper.ChatMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ChatServiceImpl implements ChatService{

    private final ChatRepository chatRepository;
    private final ChatMapper chatMapper;
    private final UserRepositoryWrapper userRepository;

    @Transactional
    @Override
    public void saveChat(Chat chat) {
        chatRepository.save(chat);
    }

    @Override
    public ChatDto createOrReturn(CheckChatDto chatDto) {
        var chat = findChatByChatName(chatDto.getChatName());
        if(Objects.isNull(chat)){
            final var userOne = userRepository.getUserByUserName(chatDto.getUserOne());
            final var userTwo = userRepository.getUserByUserName(chatDto.getUserTwo());
            chat = this.chatRepository.save(Chat.createChat(chatDto.getChatName()));
            chat.getUsers().addAll(List.of(userOne, userTwo));
        }

        return this.chatMapper.fromEntity(chat);
    }

    @Override
    public ChatDto findChatById(Long chatId) {
        return null;
    }

    @Override
    public Chat findChatByChatName(String chatName) {
        return this.chatRepository.findByChatName(chatName).orElse(null);
    }

    @Override
    public List<ChatDto> getAllChats(String username) {
        var user = userRepository.getUserByUserName(username);
        return this.chatMapper.fromEntity(this.chatRepository.findAll()
                .stream().filter(chat -> chat.getUsers().contains(user))
                .collect(Collectors.toList()));
    }
}
