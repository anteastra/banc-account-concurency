package com.test.collections;

import java.util.HashMap;
import java.util.LinkedHashMap;

/**
 * Created by anteastra on 12.06.2016.
 */
public class LinkedHashMapTest {

    public static void main(String[] args) {
        HashMap<Integer, String> map = new HashMap<>();

        map.put(5, "a");
        map.put(4, "b");
        map.put(3, "c");
        map.put(2, "d");
        map.put(1, "e");

        System.out.println(map);

        HashMap<Integer, String> lmap = new LinkedHashMap<>(5,1,true);

        lmap.put(5, "a");
        lmap.put(4, "b");
        lmap.put(3, "c");
        lmap.put(2, "d");
        lmap.put(1, "e");

        lmap.get(3);
        lmap.get(5);
        lmap.get(1);

        System.out.println(lmap);
    }
}
