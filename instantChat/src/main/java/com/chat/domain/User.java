package com.chat.domain;

import com.chat.dto.UserDto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.*;
import java.util.Set;

@Getter
@Entity
@Table(name = "users", uniqueConstraints = @UniqueConstraint(name = "UNIGUE_USERNAME", columnNames = "user_name"))
public class User extends BaseEntity {

    private String name;

    @Column(name = "user_name")
    private String userName;
    private boolean connected;

    @JsonIgnore
    @ManyToMany(mappedBy = "users", fetch = FetchType.LAZY)
    private Set<Chat> chats;

    private User(){}

    private User(String name,  String userName) {
        this.name = name;
        this.userName = userName;
    }

    public static User createUser(UserDto userDto){
        return new User(userDto.getName(), userDto.getUserName());
    }

    public void setConnected(Boolean connected) {
        this.connected = connected;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        return new EqualsBuilder().appendSuper(super.equals(o)).append(getId(), user.getId()).append(getName(), user.getName()).append(getUserName(), user.getUserName()).append(isConnected(), user.isConnected()).append(getChats(), user.getChats()).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).appendSuper(super.hashCode()).append(getId()).append(getName()).append(getUserName()).append(isConnected()).append(getChats()).toHashCode();
    }
}
