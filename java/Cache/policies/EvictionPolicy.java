package Cache.policies;

import Cache.datastructures.Node;

public interface EvictionPolicy<K, V> {
    public void onAccess(Node<K, V> node);
    public void onInsert(Node<K, V> node);
    public Node<K,V> evict();
}
