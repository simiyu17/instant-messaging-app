package com.chat.mapper;

import com.chat.domain.ChatMessage;
import com.chat.dto.ChatMessageDto;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", uses = {UserMapper.class}, unmappedSourcePolicy = ReportingPolicy.IGNORE, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ChatMessageMapper {

    ChatMessageDto fromEntity(ChatMessage chatMessage);

    List<ChatMessageDto> fromEntity(List<ChatMessage> messages);
}
