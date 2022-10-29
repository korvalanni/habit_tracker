package com.example.habitstracker.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class UserDTO {

    @EqualsAndHashCode.Include
    private String nickname;
    @EqualsAndHashCode.Include
    private String password;    
    @EqualsAndHashCode.Include
    private String habitListName;
   
}
