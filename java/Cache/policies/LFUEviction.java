package Cache.policies;

import Cache.datastructures.DoublyLinkedList;
import java.util.HashMap;
import java.util.Map;


public  class LFUEviction<K, V> implements  EvictionPolicy {

    private final Map<Integer, DoublyLinkedList<K,V>> map;
    private int accessFreq;

    public LFUEviction() {
        this.map = new HashMap<>();
    }

}