package com.test.collections;

import com.test.Account;

import java.util.*;

/**
 * Created by anteastra on 12.06.2016.
 */
public class TestCollection {

    static SortedSet<Integer> sortedSet = new TreeSet<>();
    static NavigableSet<Integer> sortedSet2 = new TreeSet<>();

    public static void main(String[] args) {
        TreeSet<Account> set = new TreeSet<>(new Comparator<Account>() {
            @Override
            public int compare(Account o1, Account o2) {
                return o1.getName().compareTo(o2.getName());
            }
        });


        set.add(new Account("1", 1000));
        set.add(new Account("4", 2000));
        set.add(new Account("3", 2001));

        for (Account a: set) {
            // System.out.println(a.getName());
        }

        for (int i=0; i < 10; i++) {
            sortedSet.add(i);
            sortedSet2.add(i);
        }

        System.out.println(getNextElement(10));

        for (Integer i: getPrevElements(3)) {
            System.out.print(i + ", ");
        }
        System.out.println();

        System.out.println(getNextElementNav(4));

        for (Integer i: getPrevElementsNav(3)) {
            System.out.print(i + ", ");
        }
    }

    private static SortedSet<Integer> getPrevElementsNav(Integer i) {
        return sortedSet2.headSet(i);
    }

    private static Integer getNextElementNav(Integer i) {
        return sortedSet2.higher(i);
    }

    private static SortedSet<Integer> getPrevElements(int i) {

        if (!sortedSet.contains(i)) {
            return new TreeSet<Integer>();
        }

        return sortedSet.subSet(sortedSet.first(), i);
    }

    private static Integer getNextElement(Integer i) {

        Iterator<Integer> iterator = sortedSet.iterator();
        while (iterator.hasNext()) {
            if (iterator.next() == i) {
                return iterator.next();
            }
        }

        return null;
    }
}
