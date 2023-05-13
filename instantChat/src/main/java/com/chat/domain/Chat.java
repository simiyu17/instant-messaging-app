package com.chat.domain;

import lombok.Getter;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Entity
@Table(name = "chat")
public class Chat extends BaseEntity {

    private String chatName;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "user_chat",
            joinColumns = {@JoinColumn(name = "chat_id")},
            inverseJoinColumns = {@JoinColumn(name = "user_id")}
    )
    private Set<User> users = new HashSet<>();

    @OneToMany(mappedBy = "chat", cascade = CascadeType.ALL)
    private List<ChatMessage> messages;

    private Chat() {
    }

    private Chat(String chatName, List<User> users, List<ChatMessage> messages) {
        this.chatName = chatName;
        this.users = new HashSet<>(users);
        this.messages = messages;
    }

    private Chat(String chatName, List<User> users) {
        this.chatName = chatName;
        this.users = new HashSet<>(users);
        this.messages = new ArrayList<>();
    }

    public static Chat createChat(String chatName){
        return Chat.createChat( chatName, new ArrayList<>(), new ArrayList<>());
    }

    public static Chat createChat(String chatName, List<User> users, List<ChatMessage> messages){
        return new Chat( chatName, users, messages);
    }

    public void addMessage(ChatMessage message){
        this.getMessages().add(message);
        message.setChat(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        Chat chat = (Chat) o;

        return new EqualsBuilder().appendSuper(super.equals(o)).append(getChatName(), chat.getChatName()).append(getUsers(), chat.getUsers()).append(getMessages(), chat.getMessages()).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).appendSuper(super.hashCode()).append(getChatName()).append(getUsers()).append(getMessages()).toHashCode();
    }
}
