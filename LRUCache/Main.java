package LRUCache;

public class Main {

    public static void main(String[] args) {
        Cache<String, Integer> cache = new Cache<>(3);

        cache.put("a", 1);
        cache.put("b", 2);
        cache.put("c", 3);

        System.out.println(cache.get("a"));

        cache.put("d", 4);

        System.out.println(cache.get("b"));
    }
}
