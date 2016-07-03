package com.test.collections;

import java.util.concurrent.CompletableFuture;

/**
 * Created by anteastra on 03.07.2016.
 */
public class AsyncCompletableFuture {

    public static Integer getSlowInteger() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return 1;
    }

    public static Integer getSlowIncrement(Integer value) {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return value++;
    }

    public static void main(String[] args) {
        CompletableFuture<Integer> future1 = CompletableFuture
                .supplyAsync(AsyncCompletableFuture::getSlowInteger)
                .thenApply(AsyncCompletableFuture::getSlowIncrement);

        CompletableFuture<Integer> future2 = CompletableFuture
                .supplyAsync(AsyncCompletableFuture::getSlowInteger)
                .thenApply(AsyncCompletableFuture::getSlowIncrement);

        CompletableFuture<?> futute3 = future1
                .thenCombine(future2, (x, y) -> x + y)
                .thenAccept(x -> System.out.printf("Result is %d\n", x));

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("The end of story. No 'future.get' method called, but async task is done");
    }
}
