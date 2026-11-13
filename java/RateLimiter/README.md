## Core requirements:

- Rate limit user requests based on a unique identifier
- Rate limiting should be tier based - different configurations for different tiers
- Support different types of rate limiting algorithms like token bucket, fixed window etc.
- Should be efficient and thread-safe

## Core entities:

- RateLimiter
- RateLimiterConfig

## Orchestrators:

We'll have a top-level orchestrator class `RateLimiterService` with which the user's request will interact.

## Class design:

Users in your application could be categorised into different tiers and different rate limiting configurations would apply to each tier. For e.g. a user in the FREE tier will experience stricter rate limits as compared to a user in the PREMIUM tier.

```
class RateLimiterService:
    - userTier: Map<String, Tier>

    + addUser(String userId, Tier tier)
    + allowRequest(String id): boolean

```

```
enum Tier:
    FREE,
    PREMIUM
```

Since the rate limit configs are not the same for all the users and will depend on the Tiers, we'll maintain a map of (tier, rate limit config) in the `RateLimiter` class:

```
class RateLimiter:
    - configMap: Map<Tier, RateLimiterConfig>

    + allowRequest(String id, Tier tier): boolean
```

The two main elements of any rate limiter are: window size and the max requests allowed within that window. These two variables will be the part of the rate limiter configuration:

```
class RateLimiterConfig:
    - maxRequests: int
    - windowInSeconds: int
```

## Implementation:

The key piece to implement is: `RateLimiter.allowRequest()`. Whether or not a request will be allowed will be decided by the rate limiting algo being used.

Because we've to support different rate limiting algorithms we'll use strategy design patten. We'll create a `RateLimitingStrategy` interface:

```
interface RateLimitingStrategy:
    - allowRequest(String id, RateLimiterConfig config): boolean
```

Then we can create concrete classes implementing this interface for different rate limiting algos:

```
class TokenBucketRateLimiter:
    - allowRequest(String id, RateLimiterConfig config): boolean
```

Now we'll have to have to create the object of this concrete class in the `RateLimiter.allowRequest()`. Because there can be multiple strategies and we would want to switch strategies easily, instead of creating the instance of the concrete strategy class directly, we'll use a factory.

```
class RateLimiterFactory:
    + getStrategy(RateLimitType type): RateLimitingStrategy
```

```
enum RateLimitType:
    TOKEN_BUCKET,
    FIXED_WINDOW
```

And in `RateLimiter.allowRequest()` we can now simply call:

```
RateLimiter.allowRequest(String id, RateLimiterConfig config):
    strategy = RateLimitingStrategyFactory.getStrategy(RateLimitType type)
    return strategy.allowRequest(id, config)
```

The actual logic to allow/reject a request is now held by a particualr strategy's `allowRequest` method.

```
class TokenBucketRateLimiter:
    - static class Bucket:
        - tokens: int
        - lastRefillTime: long
    - buckets: Map<String, Bucket>

    + allowRequest(String id, RateLimiterConfig config)
```
