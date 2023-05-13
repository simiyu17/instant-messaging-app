package com.chat.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class ChatDto implements Serializable {

    private Long id;

    private String chatName;

    public ChatDto(String chatName) {
        this.chatName = chatName;
    }
}
