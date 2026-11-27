import { Tier } from "./enums/Tier.js";
import { RateLimiterService } from "./RateLimiterService.js";
import { setTimeout } from "node:timers/promises";

async function main() {
  const service = new RateLimiterService();

  // set configs for different tiers
  service.addConfig(Tier.FREE, 5, 10);
  service.addConfig(Tier.PREMIUM, 10, 10);

  // add users in different tiers
  service.addUser("u1", Tier.FREE);
  service.addUser("u2", Tier.PREMIUM);
  service.addUser("u3", Tier.FREE);

  // fire 7 requests sequentially - all the tokens are consumed immediately
  console.log("\n===Test 1, u1===");
  for (let i = 1; i <= 7; i++) {
    const allowed = service.allowRequest("u1");
    console.log("Request " + i + " -> " + allowed); // expected: first 5 true, remaining false
  }

  // first consume all tokens in the bucket, then sleep to let the bucket refill
  console.log("\n===Test 2, u2===");
  for (let i = 1; i <= 10; i++) {
    service.allowRequest("u2");
  }

  const beforeSleep = service.allowRequest("u2");
  console.log("Request after bucket exhaustion -> " + beforeSleep);

  await setTimeout(3000);

  const afterSleep = service.allowRequest("u2");
  console.log("Request after bucket refill -> " + afterSleep);
}

await main();
