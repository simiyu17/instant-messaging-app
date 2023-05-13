package com.chat.service;

import com.chat.domain.Chat;
import com.chat.domain.ChatMessage;
import com.chat.domain.ChatMessageRepository;
import com.chat.domain.UserRepositoryWrapper;
import com.chat.dto.ChatMessageDto;
import com.chat.mapper.ChatMessageMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ChatMessageServiceImpl implements ChatMessageService{

    private final ChatMessageRepository chatMessageRepository;
    private final ChatMessageMapper chatMessageMapper;
    private final UserRepositoryWrapper userRepository;
    private final ChatService chatService;

    @Transactional
    @Override
    public void saveChatMessage(ChatMessageDto messageDto, String destination) {
        var chat = createUpdateChat(messageDto, destination);
        this.chatService.saveChat(chat);
    }

    @Override
    public List<ChatMessageDto> getChatMessages(Long chatId) {
        return this.chatMessageMapper.fromEntity(this.chatMessageRepository.findByChat_Id(chatId, Pageable.unpaged()).getContent());
    }

    @Override
    public List<ChatMessageDto> getChatMessages(String chatName) {
        return this.chatMessageMapper.fromEntity(this.chatService.findChatByChatName(chatName).getMessages());
    }

    private Chat createUpdateChat(ChatMessageDto messageDto, String destination){
        final var sender = userRepository.getUserByUserName(messageDto.getSender());
        final var receiver = userRepository.getUserByUserName(messageDto.getReceiver());
        var chat = this.chatService.findChatByChatName(destination);
        if(Objects.isNull(chat)){
            chat = Chat.createChat(destination, List.of(sender, receiver), List.of(ChatMessage.createChatMessage(messageDto)));
        }else {
            chat.addMessage(ChatMessage.createChatMessage(messageDto));
        }
        return chat;
    }
}
