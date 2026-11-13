package RateLimiter.strategies;

import RateLimiter.model.RateLimiterConfig;

public interface RateLimitingStrategy {
    boolean allowRequest(String id, RateLimiterConfig config);
}