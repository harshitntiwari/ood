import { RateLimiterType } from "../enums/RateLimiterType.js";
import { RateLimitingStrategy } from "../strategies/RateLimitingStrategy.js";
import { TokenBucketStrategy } from "../strategies/TokenBucketStrategy.js";

export class RateLimiterFactory {
  public static getStrategy(type: RateLimiterType): RateLimitingStrategy {
    switch (type) {
      case RateLimiterType.TOKEN_BUCKET: {
        return new TokenBucketStrategy();
        break;
      }
      default: {
        throw new Error(`Rate limiter type ${type} not suported`);
      }
    }
  }
}
