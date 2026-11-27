import { RateLimiterConfig } from "../model/RateLimiterConfig.js";

export interface RateLimitingStrategy {
  allowRequest(userId: string, config: RateLimiterConfig): boolean;
}
