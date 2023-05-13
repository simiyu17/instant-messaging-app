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

    public UserDto(String name, String userName) {
        this.name = name;
        this.userName = userName;
    }


}
