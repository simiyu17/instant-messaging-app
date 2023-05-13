package com.chat.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.Serializable;

@Getter
@AllArgsConstructor
public class ApiErrorMessage implements Serializable {
    private boolean success;
    private String message;
    private int status;
    private String description;
}
