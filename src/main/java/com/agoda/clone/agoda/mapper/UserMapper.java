package com.agoda.clone.agoda.mapper;

import com.agoda.clone.agoda.dto.AuthenticationResponse;
import com.agoda.clone.agoda.dto.UserResponse;
import com.agoda.clone.agoda.model.User;

import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", builder = @Builder(disableBuilder = true))
public interface UserMapper {
    @Mapping(target = "username", source = "user.email")
    @Mapping(target = "firstName", source = "user.firstname")
    @Mapping(target = "lastName", source = "user.lastname")
    @Mapping(target = "verification", source = "user.verification")
    @Mapping(target = "createDate", source = "user.createdat")
    @Mapping(target = "authenticationResponse", source = "authenticationResponse")
    UserResponse mapToUserResponse(User user, AuthenticationResponse authenticationResponse);
}
