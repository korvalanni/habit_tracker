package com.example.habitstracker.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
public class UserDTO {

    private String nickname;
    private String password;    
    private String habitListName;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserDTO userDTO)) return false;

        if (!Objects.equals(nickname, userDTO.nickname)) return false;
        if (!Objects.equals(password, userDTO.password)) return false;
        return Objects.equals(habitListName, userDTO.habitListName);
    }

    @Override
    public int hashCode() {
        int result = nickname != null ? nickname.hashCode() : 0;
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (habitListName != null ? habitListName.hashCode() : 0);
        return result;
    }
}
