package com.example.habitstracker.controllers;

import com.example.habitstracker.models.HabitList;

import javax.persistence.OneToOne;

public class UserDTO {

    private Long userId;

    private String nickname;

    private String password;
   
    private HabitList habitList;

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
