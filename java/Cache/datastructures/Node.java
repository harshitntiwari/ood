package Cache.datastructures;

public class Node<K, V> {

    public K key;
    public V value;
    public Node<K, V> prev;
    public Node<K, V> next;

    public Node(K key, V val) {
        this.key = key;
        this.value = val;
    }
}
