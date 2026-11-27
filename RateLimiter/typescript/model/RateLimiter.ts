import { RateLimiterConfig } from "./RateLimiterConfig.js";
import { Tier } from "../enums/Tier.js";
import { RateLimiterType } from "../enums/RateLimiterType.js";
import { RateLimiterFactory } from "../factory/RateLimiterFactory.js";
import { RateLimitingStrategy } from "../strategies/RateLimitingStrategy.js";

export class RateLimiter {
  private configMap: Map<Tier, RateLimiterConfig> = new Map();
  private strategy: RateLimitingStrategy;

  constructor() {
    this.strategy = RateLimiterFactory.getStrategy(
      RateLimiterType.TOKEN_BUCKET,
    );
  }

  private getConfig(tier: Tier): RateLimiterConfig | undefined {
    return this.configMap.get(tier);
  }

  public addConfig(tier: Tier, config: RateLimiterConfig) {
    this.configMap.set(tier, config);
  }

  public allowRequest(userId: string, tier: Tier): boolean {
    const config = this.getConfig(tier);
    if (!config)
      throw new Error(`No configuration available for tier: ${tier}`);
    return this.strategy.allowRequest(userId, config);
  }
}
