package com.chat.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
public class CheckChatDto implements Serializable {

    private String chatName;

    private String userOne;

    private String userTwo;
}
