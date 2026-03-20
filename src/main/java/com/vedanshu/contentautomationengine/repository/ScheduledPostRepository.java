package com.vedanshu.contentautomationengine.repository;

import com.vedanshu.contentautomationengine.entity.PostStatus;
import com.vedanshu.contentautomationengine.entity.ScheduledPost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Repository for managing scheduled social media content.
 *
 * @author Vedanshu Garg
 */
@Repository
public interface ScheduledPostRepository extends JpaRepository<ScheduledPost, Long> {

    /**
     * Retrieves all posts that are pending publication up to the current time.
     *
     * @param status the current status (e.g., SCHEDULED)
     * @param currentTime the exact time the cron job triggered
     * @return a list of posts ready to be dispatched
     */
    List<ScheduledPost> findByStatusAndScheduledTimeBefore(PostStatus status, LocalDateTime currentTime);
}
