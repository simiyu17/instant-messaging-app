package com.chat.mapper;

import com.chat.domain.Chat;
import com.chat.dto.ChatDto;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface ChatMapper {

    ChatDto fromEntity(Chat chat);

    List<ChatDto> fromEntity(List<Chat> chats);
}
