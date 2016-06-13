package com.test.collections;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.*;

/**
 * Created by anteastra on 13.06.2016.
 */
public class ConcurrentCollectionTest {

    private static int MAX_ELEMENTS = 100;
    private static int MID_ELEMENT = 50;

    private static CountDownLatch latch = new CountDownLatch(1);

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        List<Integer> list1 = Collections.synchronizedList(new ArrayList<>());
        List<Integer> list2 = new CopyOnWriteArrayList<>();

        fillList(list1, MAX_ELEMENTS);
        fillList(list2, MAX_ELEMENTS);

        System.out.println("ArrayList synchronized:");
        check(list1);

        System.out.println("CopyOnWriteArrayList:");
        check(list2);
    }

    private static void check(List<Integer> list) throws ExecutionException, InterruptedException {
        ExecutorService service = Executors.newFixedThreadPool(2);

        Future<String> future = service.submit(new Callable<String>() {
            @Override
            public String call() throws Exception {

                latch.await();
                long startTime = System.nanoTime();

                for (int i=0; i < MID_ELEMENT; i++){
                    list.get(i);
                }

                long endTime = System.nanoTime();
                return String.valueOf(endTime - startTime);
            }
        });

        Future<String> future2 = service.submit(new Callable<String>() {
            @Override
            public String call() throws Exception {
                latch.await();

                long startTime = System.nanoTime();


                for (int i=MID_ELEMENT; i < MAX_ELEMENTS; i++){
                    list.get(i);
                }

                long endTime = System.nanoTime();
                return String.valueOf(endTime - startTime);
            }
        });

        latch.countDown();

        System.out.println(future.get());
        System.out.println(future2.get());
    }

    private static void fillList(List<Integer> list, int count) {
        for (int i=0; i<count; i++) {
            list.add(i);
        }
    }
}
