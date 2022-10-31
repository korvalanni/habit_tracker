package com.example.habitstracker.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "Describe user")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class UserDTO {
    @Schema(title = "User's name")
    @EqualsAndHashCode.Include
    private String username;
    @EqualsAndHashCode.Include
    private String password;    
    @EqualsAndHashCode.Include
    private String habitListName;
   
}
