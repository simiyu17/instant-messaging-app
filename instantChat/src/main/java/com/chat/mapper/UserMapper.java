package com.chat.mapper;

import com.chat.domain.User;
import com.chat.dto.UserDto;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, unmappedSourcePolicy = ReportingPolicy.IGNORE, uses = {ChatMapper.class})
public interface UserMapper {

    UserDto fromEntity(User user);

    List<UserDto> fromEntity(List<User> users);
}
