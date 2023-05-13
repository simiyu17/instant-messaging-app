package com.chat.domain;

import com.chat.dto.ChatMessageDto;
import lombok.Getter;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.*;

@Getter
@Entity
@Table(name = "message")
public class ChatMessage extends BaseEntity implements Comparable<ChatMessage>{

    private String receiver;

    private String sender;

    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chat_id")
    private Chat chat;

    private ChatMessage(){}
    private ChatMessage(String receiver, String sender, String content) {
        this.receiver = receiver;
        this.sender = sender;
        this.content = content;
    }

    public void setChat(Chat chat) {
        this.chat = chat;
    }

    public static ChatMessage createChatMessage(ChatMessageDto chatMessageDto){
        return new ChatMessage(chatMessageDto.getReceiver(), chatMessageDto.getSender(), chatMessageDto.getContent());
    }

    @Override
    public int compareTo(ChatMessage o) {
        return o.getDateCreated().compareTo(this.getDateCreated());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        ChatMessage that = (ChatMessage) o;

        return new EqualsBuilder().appendSuper(super.equals(o)).append(getReceiver(), that.getReceiver()).append(getSender(), that.getSender()).append(getContent(), that.getContent()).append(getChat(), that.getChat()).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).appendSuper(super.hashCode()).append(getReceiver()).append(getSender()).append(getContent()).append(getChat()).toHashCode();
    }
}
