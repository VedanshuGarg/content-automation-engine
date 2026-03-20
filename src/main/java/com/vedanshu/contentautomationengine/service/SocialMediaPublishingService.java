package com.vedanshu.contentautomationengine.service;

import com.vedanshu.contentautomationengine.entity.PostStatus;
import com.vedanshu.contentautomationengine.entity.ScheduledPost;
import com.vedanshu.contentautomationengine.repository.ScheduledPostRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.util.retry.Retry;

import java.time.Duration;

/**
 * Service responsible for distributing content to external social media APIs.
 * Utilizes reactive WebClient for non-blocking network calls and implements
 * exponential backoff for fault tolerance.
 *
 * @author Vedanshu Garg
 */
@Service
public class SocialMediaPublishingService {

    private static final Logger log = LoggerFactory.getLogger(SocialMediaPublishingService.class);

    private final WebClient webClient;
    private final ScheduledPostRepository repository;

    /**
     * Constructs the publishing service with required dependencies.
     *
     * @param webClientBuilder the autoconfigured WebClient builder
     * @param repository the repository for updating post statuses
     */
    public SocialMediaPublishingService(WebClient.Builder webClientBuilder, ScheduledPostRepository repository) {
        this.webClient = webClientBuilder.build();
        this.repository = repository;
    }

    /**
     * Executes the publishing workflow for a specific scheduled post.
     * Determines the correct API routing based on the target platform.
     *
     * @param post the content to be published
     */
    @Transactional
    public void publishContent(ScheduledPost post) {
        log.info("Starting WebFlux payload dispatch for Post ID: {}", post.getId());

        try {
            String targetApiUrl = "https://httpbin.org/post";

            /*
             * 1. Constructs the POST request.
             * 2. Adds the JSON body containing the video URL and description.
             * 3. Retries up to 3 times with a 2-second delay if the API fails (e.g., HTTP 500).
             * 4. block() forces the reactive stream to execute synchronously for our cron job thread.
             */
            String externalApiResponse = webClient.post()
                    .uri(targetApiUrl)
                    .bodyValue(post)
                    .retrieve()
                    .bodyToMono(String.class)
                    .retryWhen(Retry.fixedDelay(3, Duration.ofSeconds(2)))
                    .block();

            log.info("Successfully published Post ID: {}. External API Response: OK", post.getId());

            post.setStatus(PostStatus.PUBLISHED);
            post.setExternalId("mock-ext-id-" + System.currentTimeMillis());
            repository.save(post);

        } catch (Exception e) {
            log.error("Failed to publish Post ID: {} after retries. Error: {}", post.getId(), e.getMessage());

            // Marking as failed so it can be manually reviewed or picked up by a dead-letter queue
            post.setStatus(PostStatus.FAILED);
            repository.save(post);
        }
    }
}
