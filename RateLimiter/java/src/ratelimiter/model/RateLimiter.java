package ratelimiter.model;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import ratelimiter.enums.RateLimiterType;
import ratelimiter.enums.Tier;
import ratelimiter.factory.RateLimiterFactory;
import ratelimiter.strategies.RateLimitingStrategy;

public class RateLimiter {
    private final Map<Tier, RateLimiterConfig> configMap;
    private final RateLimitingStrategy strategy;

    public RateLimiter() {
        configMap = new ConcurrentHashMap<>();
        strategy = RateLimiterFactory.getRateLimiterStrategy(RateLimiterType.TOKEN_BUCKET);
    }

    public void addConfig(Tier tier, RateLimiterConfig config) {
        configMap.put(tier, config);
    }

    public RateLimiterConfig getConfigByTier(Tier tier) {
        return configMap.get(tier);
    }

    public boolean allowRequest(String id, Tier tier) {
        RateLimiterConfig config = getConfigByTier(tier);
        return strategy.allowRequest(id, config);
    }
}