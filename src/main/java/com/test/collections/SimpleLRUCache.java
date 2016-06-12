package com.test.collections;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by anteastra on 12.06.2016.
 */
public class SimpleLRUCache<K, V> extends LinkedHashMap<K, V> {

    private final int capacity;

    public SimpleLRUCache(int capacity) {
        super(capacity + 1, 1.1f, true);
        this.capacity = capacity;
    }

    @Override
    protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
        return this.size() > capacity;
    }

    public static void main(String[] args) {
        SimpleLRUCache<Integer, String> lru = new SimpleLRUCache<>(2);

        lru.put(1, "a");
        lru.put(2, "b");
        lru.put(3, "c");

        lru.get(2);
        lru.put(9, "d");

        System.out.println(lru);
    }
}
