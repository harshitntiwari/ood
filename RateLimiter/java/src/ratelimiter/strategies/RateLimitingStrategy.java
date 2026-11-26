package ratelimiter.strategies;

import ratelimiter.model.RateLimiterConfig;

public interface RateLimitingStrategy {
    boolean allowRequest(String id, RateLimiterConfig config);
}