package RateLimiter.factory;

import RateLimiter.enums.RateLimiterType;
import RateLimiter.strategies.RateLimitingStrategy;
import RateLimiter.strategies.TokenBucketRateLimiter;


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