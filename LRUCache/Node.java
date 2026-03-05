package LRUCache;

public class Node<K, V> {

    K key;
    V value;
    Node<K, V> prev;
    Node<K, V> next;

    public Node(K key, V val) {
        this.key = key;
        this.value = val;
    }
}
