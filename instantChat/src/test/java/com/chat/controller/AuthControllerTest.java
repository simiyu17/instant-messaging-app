package com.chat.controller;

import com.chat.UnitTestBase;
import com.chat.dto.UserDto;
import com.chat.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@WebMvcTest(AuthController.class)
class AuthControllerTest extends UnitTestBase {

    @MockBean
    private UserService userService;

    @MockBean
    private SimpMessagingTemplate template;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void findConnectedUsers() throws Exception {
        List<UserDto> users = List.of(new UserDto("Daniel Simiyu", "simiyu", ""));
        when(userService.getAllUsers()).thenReturn(users);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/users")
                .contentType(MediaType.APPLICATION_JSON_VALUE);

        MvcResult result = mockMvc.perform(requestBuilder)
                .andReturn();
        verify(userService, times(1)).getAllUsers();
        JSONAssert.assertEquals("[{name: \"Daniel Simiyu\", userName: simiyu}]", result.getResponse().getContentAsString(), false);

    }
}