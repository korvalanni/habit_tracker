package com.example.habitstracker.models;

import javax.persistence.*;
import java.util.Objects;

import com.example.habitstracker.models.listeners.UserListener;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "users")
@EntityListeners(UserListener.class)
@Getter
@Setter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    private String nickname;

    private String password;

    @OneToOne
    private HabitList habitList;

    public User() {

    }

    public User(Long userId, String nickname, String password, HabitList habitList) {
        this.userId = userId;
        this.nickname = nickname;
        this.password = password;
        this.habitList = habitList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User user)) return false;

        return Objects.equals(userId, user.userId);
    }

    @Override
    public int hashCode() {
        return userId != null ? userId.hashCode() : 0;
    }
}
