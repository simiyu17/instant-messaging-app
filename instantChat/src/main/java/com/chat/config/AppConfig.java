package com.chat.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class AppConfig {

    @Value("${chat.api.url}")
    private String backendURL;

    @Value("${chat.front.end.url}")
    private String frontEndURL;

    public String getBackendURL() {
        return backendURL;
    }

    public String getFrontEndURL() {
        return frontEndURL;
    }
}
