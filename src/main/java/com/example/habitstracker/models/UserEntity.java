package com.example.habitstracker.models;

import com.example.habitstracker.constants.TableNameConstants;
import com.example.habitstracker.models.listeners.UserListener;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = TableNameConstants.USER)
@EntityListeners(UserListener.class)
@Getter
@Setter
@NoArgsConstructor
public class UserEntity implements Cloneable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;
    @Column(unique = true)
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        UserEntity that = (UserEntity) o;
        return userId != null && Objects.equals(userId, that.userId);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
