package com.example.habitstracker.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
@Schema(description = "Describe user")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class UserDTO {
    @Schema(title = "User's nickname")
    @EqualsAndHashCode.Include
    private String nickname;
    @EqualsAndHashCode.Include
    private String password;    
    @EqualsAndHashCode.Include
    private String habitListName;
   
}
