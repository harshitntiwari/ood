package ratelimiter;

import ratelimiter.enums.Tier;

public class Main {
    public static void main(String[] args) throws InterruptedException{
        RateLimiterService service = new RateLimiterService();

        // set configs for different tiers
        service.addConfig(Tier.FREE, 5, 10);
        service.addConfig(Tier.PREMIUM, 10, 10);

        // add users in different tiers
        service.addUser("u1", Tier.FREE);
        service.addUser("u2", Tier.PREMIUM);
        service.addUser("u3", Tier.FREE);

        // fire 7 requests sequentially - all the tokens are consumed immediately
        System.out.println("\n===Test 1, u1===");
        for(int i = 1; i <= 7; i++) {
            boolean allowed = service.allowRequest("u1");
            System.out.println("Request " + i + " -> " + allowed); // expected: first 5 true, remaining false
        }

        // first consume all tokens in the bucket, then sleep to let the bucket refill
        System.out.println("\n===Test 2, u2===");
        for(int i = 1; i <= 10; i++) {
            service.allowRequest("u2");
        }
        boolean beforeSleep = service.allowRequest("u2");
        System.out.println("Request after bucket exhaustion "  + " -> " + beforeSleep); // expected: false
        Thread.sleep(3000);
        boolean afterSleep = service.allowRequest("u2");
        System.out.println("Request after bucket refill "  + " -> " + afterSleep); // expected: true

    }
}