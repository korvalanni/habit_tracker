package com.example.habitstracker.models;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "users")
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

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public HabitList getHabitList() {
        return habitList;
    }

    public void setHabitList(HabitList habitList) {
        this.habitList = habitList;
    }
    //toDO equals hashcode

}
