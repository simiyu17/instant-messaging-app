package com.chat.domain;

import javax.persistence.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

@MappedSuperclass
public class BaseEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @CreatedDate
    private LocalDateTime dateCreated;

    @LastModifiedDate
    private LocalDateTime lastModified;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getDateCreated() {
        return dateCreated;
    }

    @PrePersist
    public void setDateCreated() {
        this.dateCreated = LocalDateTime.now();
    }

    public LocalDateTime getLastModified() {
        return lastModified;
    }

    @PreUpdate
    public void setLastModified() {
        this.lastModified = LocalDateTime.now();
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (this.getId() != null ? this.getId().hashCode() : 0);

        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null) {
            return false;
        }
        if (getClass() != object.getClass()) {
            return false;
        }

        BaseEntity other = (BaseEntity) object;
        return Objects.equals(this.getId(), other.getId()) || (this.getId() != null && this.id.equals(other.id));
    }

    @Override
    public String toString() {
        return this.getClass().getName() + " [ID=" + id + "]";
    }

}
