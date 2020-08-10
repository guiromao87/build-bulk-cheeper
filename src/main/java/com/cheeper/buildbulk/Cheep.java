package com.cheeper.buildbulk;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Cheep {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(columnDefinition = "text")
    private String message;
    private LocalDateTime creation = LocalDateTime.now();

    @ManyToOne
    private User profile;

    public Long getId() {
        return id;
    }

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
