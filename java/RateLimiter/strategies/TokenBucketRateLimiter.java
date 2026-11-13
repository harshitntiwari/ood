package RateLimiter.strategies;

import RateLimiter.model.RateLimiterConfig;
import java.time.Duration;
import java.time.Instant;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class TokenBucketRateLimiter implements RateLimitingStrategy {

    private static class Bucket {
        double tokens;
        Instant lastRefillTime;

        public Bucket(int capacity) {
            tokens = capacity;
            lastRefillTime = Instant.now();
        }
    }

    private final Map<String, Bucket> buckets = new ConcurrentHashMap<>();

    private void refill(Bucket bucket, RateLimiterConfig config) {
        Instant now = Instant.now();
        long elapsedSeconds = Duration.between(bucket.lastRefillTime, now).toSeconds();

        if(elapsedSeconds > 0 ) {
            double refillRate = (double) config.getMaxRequests() / config.getWindowInSeconds(); // tokens to be refilled per sec
            bucket.tokens = Math.min(config.getMaxRequests(), bucket.tokens + (refillRate * elapsedSeconds));
            bucket.lastRefillTime = now;
        }
    }

    @Override
    public boolean allowRequest(String id, RateLimiterConfig config) {
        // create a bucket if does not already exists for this user
        Bucket bucket = buckets.computeIfAbsent(id, v -> new Bucket(config.getMaxRequests())); // capacity of a bucket is capped at maxRequests allowed in a window
        
        // refill and consume
        synchronized (bucket) {
            refill(bucket, config);

            if(bucket.tokens > 0) { // consume 1 token if available
                bucket.tokens--;
                return true;
            }
            return false;
        }
    }
}