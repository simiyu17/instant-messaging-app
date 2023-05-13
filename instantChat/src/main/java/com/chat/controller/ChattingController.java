package com.chat.controller;

import com.chat.dto.ChatMessageDto;
import com.chat.service.ChatMessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ChattingController {

    private final SimpMessagingTemplate template;

    private final ChatMessageService chatMessageService;

    /**
     * Sends a message to its destination channel/topic
     *
     */
    @MessageMapping("/ws/{destination}")
    public void handleMessage(@DestinationVariable("destination")String destination, ChatMessageDto message) {
        chatMessageService.saveChatMessage(message, destination);
        template.convertAndSend("/topic/messages/" + destination, message);
    }
}
