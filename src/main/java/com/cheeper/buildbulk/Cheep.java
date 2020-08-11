package com.cheeper.buildbulk;

import org.springframework.data.domain.Persistable;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Cheep implements Persistable {

    @Id
    private Long id;
    
    @Column(columnDefinition = "text")
    private String message;
    private LocalDateTime creation = LocalDateTime.now();

    @ManyToOne
    private User profile;

    public Long getId() {
        return id;
    }

    @Override
    public boolean isNew() { return true; }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LocalDateTime getCreation() { return creation; }

    public User getProfile() { return profile; }

    public void setProfile(User profile) { this.profile = profile; }

    public boolean isOwnedBy(User loggedUser) {
        return this.profile.getId().equals(loggedUser.getId());
    }
}
