import { RateLimiterConfig } from "../model/RateLimiterConfig.js";
import { RateLimitingStrategy } from "./RateLimitingStrategy.js";

export class TokenBucketStrategy implements RateLimitingStrategy {
  private buckets: Map<string, Bucket> = new Map();

  private refill(bucket: Bucket, config: RateLimiterConfig) {
    const now = Date.now();
    const elapsedSeconds = (now - bucket.lastRefillTime) / 1000;
    if (elapsedSeconds > 0) {
      const refillRate = config.maxRequests / config.windowInSeconds; // tokens to be refilled per sec
      bucket.tokens = Math.min(
        config.maxRequests,
        bucket.tokens + elapsedSeconds * refillRate,
      );
      bucket.lastRefillTime = now;
    }
  }

  public allowRequest(userId: string, config: RateLimiterConfig): boolean {
    let bucket = this.buckets.get(userId);
    if (!bucket) {
      // create a bucket if does not already exists for this user
      this.buckets.set(userId, new Bucket(config.maxRequests));
      bucket = this.buckets.get(userId);
    }

    if (!bucket) return false;

    // refill and consume
    this.refill(bucket, config);

    if (bucket.tokens >= 1) {
      bucket.tokens--; // consume 1 token if available
      return true;
    }
    return false;
  }
}

class Bucket {
  tokens: number;
  lastRefillTime: number;

  constructor(capacity: number) {
    this.tokens = capacity;
    this.lastRefillTime = Date.now();
  }
}
