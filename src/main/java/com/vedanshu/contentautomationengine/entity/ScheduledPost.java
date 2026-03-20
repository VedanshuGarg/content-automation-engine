package com.vedanshu.contentautomationengine.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

/**
 * Represents a piece of content scheduled for distribution to a social platform.
 * Includes lifecycle tracking to prevent duplicate publishing.
 *
 * @author Vedanshu Garg
 */
@Entity
@Table(name = "scheduled_posts")
public class ScheduledPost {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String title;

    @Column(length = 2000)
    private String description;

    @Column(nullable = false, name = "media_url")
    private String mediaUrl; // URL to the video file (e.g., S3 bucket or local path)

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SocialPlatform platform;

    @Column(nullable = false, name = "scheduled_time")
    private LocalDateTime scheduledTime;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PostStatus status;

    @Column(name = "external_id")
    private String externalId; // The ID returned by YouTube/Instagram after publishing

    public ScheduledPost() {}

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getMediaUrl() {
        return mediaUrl;
    }

    public SocialPlatform getPlatform() {
        return platform;
    }

    public LocalDateTime getScheduledTime() {
        return scheduledTime;
    }

    public PostStatus getStatus() {
        return status;
    }

    public String getExternalId() {
        return externalId;
    }
}
