package Cache.policies;

import Cache.datastructures.DoublyLinkedList;
import Cache.datastructures.Node;

public class LRUEviction<K, V> implements EvictionPolicy {

    private final DoublyLinkedList<K, V> dll;

    public LRUEviction() {
        this.dll = new DoublyLinkedList<>();
    }

    @Override
    public void onAccess(Node node) {
       dll.moveToFront(node);
    }

    @Override
    public void onInsert(Node node) {
       dll.addFirst(node);
    }

    @Override
    public Node<K,V> evict() {
        Node<K,V> node = dll.removeLast();
        return node;
    }
}
