import { Tier } from "./enums/Tier";
import { RateLimiter } from "./model/RateLimiter";
import { RateLimiterConfig } from "./model/RateLimiterConfig";

export class RateLimiterService {
  private usersMap: Map<string, Tier> = new Map();
  private limiter: RateLimiter = new RateLimiter();

  public addConfig(tier: Tier, maxRequests: number, windowInSeconds: number) {
    this.limiter.addConfig(
      tier,
      new RateLimiterConfig(maxRequests, windowInSeconds),
    );
  }

  public addUser(userId: string, tier: Tier): void {
    this.usersMap.set(userId, tier);
  }

  public allowRequest(userId: string): boolean {
    const tier = this.usersMap.get(userId) ?? Tier.FREE;
    return this.limiter.allowRequest(userId, tier);
  }
}
