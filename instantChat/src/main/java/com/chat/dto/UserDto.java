package com.chat.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class UserDto implements Serializable {

    private Long id;
    private String name;
    private String userName;
    private boolean connected;
    private String authToken;

    public UserDto(String name, String userName, String authToken) {
        this.name = name;
        this.userName = userName;
        this.authToken = authToken;
    }



}
