package org.library.library_management.mapper;

import org.library.library_management.dto.user.UserRequestDTO;
import org.library.library_management.dto.user.UserResponseDTO;
import org.library.library_management.models.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);
    User toUser(UserRequestDTO userRequestDTO);
    UserResponseDTO toUserResponseDTO(User user);
}
