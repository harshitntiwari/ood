Core Requirements:
1. The Cache should support the following operations. The `put` and `get` methods should be efficient, ideally O(1).
   - `put(key, val)` : Put the key-value pair in the cache. if the key is already present, overwrite the value. If the cache is full, use the Cache eviction policy as mentioned in point 3.
   - `get(key)` : Return the value corresponding to the `key`.
    - If the key exists, move it to the front of the cache, then return value.
    - If the key does not exist, return -1.
  - `remove(key)` : Remove a key from the cache.
2. The Cache should have a fixed capacity.
3. Cache eviction policy to be used: Least Recently Used (LRU).
  - If the cache reaches it capacity, the least recently used key should be removed to accomodate new keys
4. The Cache should be thread-safe, allowing concurrent access from multiple threads

Reasoning for what data structures to be used:
- For fast insertion and retrieval we could've simply used a HashMap. But our cache size is not infinite. When the cache reaches it's capacity, we need to do eviction of the LRU element which also needs to be fast. And because this eviction is happening on LRU basis, we also need to track the priority order of the elements (based on how recently they were accessed) to be removed. For this we can use a list with the most recently used element at the front and the least recently used element at the last.
- Because the insertion (at front) and eviction (from last) should be fast, we need to keep track of both the front and last of the list. 
- Moreover, for every element, we should know the element just before it and just after it, so that the removal of any element is O(1).
- However because retrieval will still be O(n) in a List, we'll additionally keep a HashMap for O(1) retrievals.

Based on the above points, we'll use a HashMap for fast retrieval, and a DoublyLinkedList for fast insertion and eviction

Entities:
- Cache: Will have a HashMap and a DoublyLinkedList
- DoublyLinkedList: With a head and tail to track the first and the last element.
- Node: Denoting a key-value pair. Each node will have a reference to the element just before it and the element just after it.
