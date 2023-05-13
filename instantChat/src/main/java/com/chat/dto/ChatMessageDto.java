package com.chat.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
public class ChatMessageDto implements Serializable {

    private Long id;
    private String receiver;
    private String sender;
    private String content;
    private LocalDateTime dateCreated = LocalDateTime.now();

    public ChatMessageDto(String receiver, String sender, String content) {
        this.receiver = receiver;
        this.sender = sender;
        this.content = content;
    }

}
