package LRUCache;

/*
In this implementation of DoublyLinkedList we're keeping the head and tail as permanent sentinel (dummy) nodes.
All the actual nodes will be inserted between these two sentinel nodes.
These sentinel nodes help eliminate handling of edge cases like when the list is empty, list with just one element or
operations on first and last nodes.
With the existence of these sentinel nodes, every node can be treated in the same manner without having to
worry about edge cases, enabling uniform operations and cleaner code.
*/

public class DoublyLinkedList<K, V> {

    private Node<K, V> head;
    private Node<K, V> tail;

    public DoublyLinkedList() {
        // sentinel nodes for head and tail:
        head = new Node<>(null, null);
        tail = new Node<>(null, null);

        head.next = tail;
        tail.prev = head;
    }

    public void addFirst(Node<K, V> node) {
        node.next = head.next;
        node.prev = head;

        head.next.prev = node;
        head.next = node;
    }

    public void remove(Node<K, V> node) {
        node.prev.next = node.next;
        node.next.prev = node.prev;
    }

    public void moveToFront(Node<K, V> node) {
        remove(node);
        addFirst(node);
    }

    public Node<K, V> removeLast() {
        if (tail.prev == head) return null; // there are no nodes to remove
        Node<K, V> lastNode = tail.prev;
        remove(lastNode);
        return lastNode;
    }
}
