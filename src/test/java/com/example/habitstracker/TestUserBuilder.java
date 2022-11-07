package com.example.habitstracker;

import com.example.habitstracker.models.HabitList;
import com.example.habitstracker.models.User;

public class TestUserBuilder {
    private long id = 0;
    private String username = "Nick";
    private String password = "Passwd2!";
    private HabitList habitList = new HabitList("Habits");

    public TestUserBuilder setId(long id) {
        this.id = id;
        return this;
    }

    public TestUserBuilder setUsername(String username) {
        this.username = username;
        return this;
    }

    public TestUserBuilder setPassword(String password) {
        this.password = password;
        return this;
    }

    public TestUserBuilder setHabitList(HabitList habitList) {
        this.habitList = habitList;
        return this;
    }

    public User build() {
        return new User(id, username, password, habitList);
    }
}
