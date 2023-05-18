package com.chat;

import com.chat.security.CurrentUserDetails;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;

@WithMockUser
public class UnitTestBase {

    @MockBean
    protected CurrentUserDetails userDetails;

    protected ObjectWriter getObjectWritter(){
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        return mapper.writer().withDefaultPrettyPrinter();
    }
}
