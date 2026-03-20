package com.vedanshu.contentautomationengine.scheduler;

import com.vedanshu.contentautomationengine.entity.PostStatus;
import com.vedanshu.contentautomationengine.entity.ScheduledPost;
import com.vedanshu.contentautomationengine.repository.ScheduledPostRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;

/**
 * Background worker responsible for polling the database and triggering
 * the social media distribution workflows at the correct timestamps.
 *
 * @author Vedanshu Garg
 */
@Service
public class ContentScheduler {

    private static final Logger log = LoggerFactory.getLogger(ContentScheduler.class);
    private final ScheduledPostRepository repository;

    public ContentScheduler(ScheduledPostRepository repository) {
        this.repository = repository;
    }

    /**
     * Cron job that executes every minute at the top of the minute (00 seconds).
     * It finds all pending posts and hands them off to the publisher service.
     */
    @Scheduled(cron = "0 * * * * *")
    @Transactional
    public void processScheduledContent() {
        LocalDateTime now = LocalDateTime.now(ZoneOffset.UTC);
        log.info("Waking up scheduler to check for pending content at {}", now);

        List<ScheduledPost> pendingPosts = repository.findByStatusAndScheduledTimeBefore(PostStatus.SCHEDULED, now);

        if (pendingPosts.isEmpty()) {
            log.debug("No content scheduled for publication at this time.");
            return;
        }

        for (ScheduledPost post : pendingPosts) {
            log.info("Initiating publishing workflow for Post ID: {} to Platform: {}", post.getId(), post.getPlatform());

            // We temporarily set status to DRAFT or a new 'PROCESSING' state here to prevent
            // the next cron cycle from picking it up if the upload takes longer than 60 seconds
            // (In a full distributed system, we would use a message queue like Kafka/RabbitMQ)

            // TODO: Pass to SocialMediaPublishingService
        }
    }
}
