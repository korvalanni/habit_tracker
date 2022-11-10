package com.example.habitstracker.models;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.example.habitstracker.models.listeners.UserListener;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "users")
@EntityListeners(UserListener.class)
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
public class User implements Cloneable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long userId;

    private String username;

    private String password;

    @OneToOne
    private HabitList habitList;

    public User(Long userId, String username, String password, HabitList habitList) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.habitList = habitList;
    }

    /**
     * Make shallow copy
     */
    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
