export class RateLimiterConfig {
  private readonly _maxRequests: number;
  private readonly _windowInSeconds: number;

  constructor(maxRequests: number, windowInSeconds: number) {
    this._maxRequests = maxRequests;
    this._windowInSeconds = windowInSeconds;
  }

  get maxRequests(): number {
    return this._maxRequests;
  }

  get windowInSeconds(): number {
    return this._windowInSeconds;
  }
}
