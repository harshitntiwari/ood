package RateLimiter;

import RateLimiter.enums.Tier;
import RateLimiter.model.RateLimiter;
import RateLimiter.model.RateLimiterConfig;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class RateLimiterService {
    private final Map<String, Tier> userTierMap;
    private final RateLimiter limiter;

    public RateLimiterService() {
        userTierMap = new ConcurrentHashMap<>();
        limiter = new RateLimiter();
    }

    public void addConfig(Tier tier, int maxRequests, int windowInSeconds) {
        limiter.addConfig(tier, new RateLimiterConfig(maxRequests, windowInSeconds));
    }

    public void addUser(String userId, Tier tier) {
        userTierMap.put(userId, tier);
    }

    public boolean allowRequest(String id) {
        Tier userTier = userTierMap.get(id) != null ? userTierMap.get(id) : Tier.FREE; // default tier is FREE
        return limiter.allowRequest(id, userTier);
    }
}