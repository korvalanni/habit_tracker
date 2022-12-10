package com.example.habitstracker.models;

import com.example.habitstracker.constants.TableNameConstants;
import com.example.habitstracker.models.listeners.PasswordListener;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = TableNameConstants.USER)
@EntityListeners(PasswordListener.class)
@Getter
@Setter
@NoArgsConstructor
public class UserPassword {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;
    private String password;
    // todo: inherit userEntity from userPassword

    public UserPassword(Long userId, String password) {
        this.userId = userId;
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        UserPassword that = (UserPassword) o;
        return userId != null && Objects.equals(userId, that.userId);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
