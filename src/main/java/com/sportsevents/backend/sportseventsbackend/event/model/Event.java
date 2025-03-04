package com.sportsevents.backend.sportseventsbackend.event.model;

import com.sportsevents.backend.sportseventsbackend.user.model.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

@Getter
@Setter
@Entity
@SQLDelete(sql = "UPDATE events SET is_deleted = true WHERE id=?")
@SQLRestriction(value = "is_deleted=false")
@Table(name = "events")
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String title;
    @Column(name = "description_small", nullable = false)
    private String descriptionSmall;
    @Column(name = "description_full", nullable = false)
    private String descriptionFull;
    @Column(name = "avatar_image")
    private String avatarImage;
    @Column(name = "main_image")
    private String mainImage;
    @Column(name = "video_link")
    private String videoLink;
    @Column(name = "maximum_participants", nullable = false)
    private BigDecimal maximumParticipants;
    @Column(name = "date_of_start_event",nullable = false)
    private LocalDateTime dateOfStartEvent;
    @Column(nullable = false)
    private BigDecimal price;
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
    @Column(nullable = false, updatable = false)
    private String city;
    @Column(nullable = false, updatable = false)
    private String google_map_coordinates;

    @ManyToOne
    @JoinColumn(name = "author_id", nullable = false)
    private User author;
    @Column(name = "is_deleted", columnDefinition = "TINYINT(1)")
    private boolean isDeleted = false;
    @Column(name = "registration_available_until", nullable = false)
    private LocalDateTime registrationAvailableUntil;

    @ManyToMany
    @JoinTable(
            name = "events_categories",
            joinColumns = @JoinColumn(name = "event_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id"))
    private Set<Category> categories = new HashSet<>();

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
}
