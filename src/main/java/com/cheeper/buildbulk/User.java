package com.cheeper.buildbulk;

import org.springframework.data.domain.Persistable;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
public class User implements Persistable {

    @Id
    private Integer id;
    private String email;
    private String password;
    private String name;
    private String bio;
    private String image;
    private LocalDate created = LocalDate.now();
    private String profileName;
    private boolean verifiedEmail;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name="relationship",
            joinColumns=@JoinColumn(name="follower_id"),
            inverseJoinColumns=@JoinColumn(name="followed_id"))
    private Set<User> following = new HashSet<>();

    public Set<User> getFollowing() { return Collections.unmodifiableSet(following); }

    public boolean isFollowing(User profile) {
        return this.following.contains(profile);
    }

    public void follow(User toBeFollowed) {
        this.following.add(toBeFollowed);
    }

    public void unfollow(User followed) { this.following.remove(followed); }

    public Integer getId() { return id; }

    @Override
    public boolean isNew() { return true; }

    public void setId(Integer id) { this.id = id; }

    public String getEmail() { return email; }

    public void setEmail(String email) { this.email = email; }

    public void setPassword(String password) { this.password = password; }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public String getBio() { return bio; }

    public void setBio(String bio) { this.bio = bio; }

    public String getImage() { return image; }

    public void setImage(String image) { this.image = image; }

    public LocalDate getCreated() { return created; }

    public String getProfileName() { return profileName; }

    public void setProfileName(String profileName) { this.profileName = profileName; }

    public boolean isVerifiedEmail() { return verifiedEmail; }

    public void setVerifiedEmail(boolean verifiedEmail) { this.verifiedEmail = verifiedEmail; }

    public boolean isNotTheSameAs(User loggedUser) {
        return !this.getId().equals(loggedUser.getId());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return profileName.equals(user.profileName);
    }

    @Override
    public int hashCode() { return Objects.hash(profileName); }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", profileName='" + profileName + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}

