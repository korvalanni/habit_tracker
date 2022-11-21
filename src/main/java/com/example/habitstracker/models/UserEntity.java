package com.example.habitstracker.models;

import javax.persistence.*;

import com.example.habitstracker.constants.TableNameConstants;
import com.example.habitstracker.models.listeners.UserListener;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = TableNameConstants.USER)
@EntityListeners(UserListener.class)
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
public class UserEntity implements Cloneable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long userId;

    private String username;

    private String password;

    @OneToOne(cascade = CascadeType.ALL)
    private HabitList habitList;

    public UserEntity(Long userId, String username, String password, HabitList habitList) {
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
