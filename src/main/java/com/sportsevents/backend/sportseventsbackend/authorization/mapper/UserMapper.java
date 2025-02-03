package com.sportsevents.backend.sportseventsbackend.authorization.mapper;

import com.sportsevents.backend.sportseventsbackend.authorization.dto.user.UserDto;
import com.sportsevents.backend.sportseventsbackend.authorization.dto.user.UserRegistrationRequestDto;
import com.sportsevents.backend.sportseventsbackend.authorization.model.User;
import com.sportsevents.backend.sportseventsbackend.common.config.MapperConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MapperConfig.class)
public interface UserMapper {
    UserDto toDto(User user);

    User toModel(UserRegistrationRequestDto requestDto);
}
