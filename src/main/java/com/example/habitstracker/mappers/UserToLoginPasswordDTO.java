package com.example.habitstracker.mappers;

import com.example.habitstracker.models.UserEntity;
import com.example.openapi.dto.LoginPasswordDTO;
import org.springframework.stereotype.Component;

@Component
public class UserToLoginPasswordDTO extends Mapper<UserEntity, LoginPasswordDTO> {
    public UserToLoginPasswordDTO() {
        super(UserEntity.class, LoginPasswordDTO.class);
    }

    @Override
    public void map(UserEntity from, LoginPasswordDTO to) {
        to.setUsername(from.getUsername());
        to.setPassword(from.getPassword());
    }
}
