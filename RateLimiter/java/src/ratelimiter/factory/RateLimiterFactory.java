package ratelimiter.factory;

import ratelimiter.enums.RateLimiterType;
import ratelimiter.strategies.RateLimitingStrategy;
import ratelimiter.strategies.TokenBucketRateLimiter;


public class RateLimiterFactory {
    public static RateLimitingStrategy getRateLimiterStrategy(RateLimiterType type){
        switch (type) {
            case RateLimiterType.TOKEN_BUCKET -> { 
                return new TokenBucketRateLimiter();
            }
            default -> throw new IllegalArgumentException("Unknown algorithm: " + type);
        }
    }
}