package LRUCache;

import LRUCache.DoublyLinkedList;
import java.util.HashMap;
import java.util.Map;

public class Cache<K, V> {

    private final int capacity;
    private final Map<K, Node<K, V>> map;
    private final DoublyLinkedList<K, V> dll;

    public Cache(int capacity) {
        this.capacity = capacity;
        this.map = new HashMap<>();
        this.dll = new DoublyLinkedList<>();
    }

    public V get(K key) {
        if (!map.containsKey(key)) return null;
        Node<K, V> node = map.get(key);
        dll.moveToFront(node);
        return node.value;
    }

    public void put(K key, V value) {
        if (map.containsKey(key)) {
            // if the key already exists overwrite the value
            Node<K, V> node = map.get(key);
            node.value = value;
            dll.moveToFront(node);
        } else {
            if (map.size() == capacity) {
                Node<K, V> lastNode = dll.removeLast();
                if (lastNode != null) map.remove(lastNode.key);
            }
            Node<K, V> nodeToBeAdded = new Node<K, V>(key, value);
            dll.addFirst(nodeToBeAdded);
            map.put(key, nodeToBeAdded);
        }
    }

    public void remove(K key) {
        if (!map.containsKey(key)) return;
        Node<K, V> nodeToBeRemoved = map.get(key);
        dll.remove(nodeToBeRemoved);
        map.remove(key);
    }
}
