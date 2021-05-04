package com.unialto.test.completableFuture;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

/**
 * CompletableFuture.allOf
 * <p>
 * supplyAsync >
 * supplyAsync >
 * ...
 * supplyAsync > thenAccept
 * </p>
 */
public class Test7 {
    public static void main(String[] args) throws Exception {
        System.out.printf("[%s] main start \n", Thread.currentThread().getName());

        CompletableFuture[] futures = {
                CompletableFuture.supplyAsync(() -> {
                    try {
                        System.out.printf("[%s] supplyAsync-1 start \n", Thread.currentThread().getName());
                        Thread.sleep(1000);
                        System.out.printf("[%s] supplyAsync-1 end \n", Thread.currentThread().getName());
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    return "hello,";
                }),
                CompletableFuture.supplyAsync(() -> {
                    try {
                        System.out.printf("[%s] supplyAsync-2 start \n", Thread.currentThread().getName());
                        Thread.sleep(1000);
                        System.out.printf("[%s] supplyAsync-2 end \n", Thread.currentThread().getName());
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    return " world.";
                }),
                CompletableFuture.supplyAsync(() -> {
                    try {
                        System.out.printf("[%s] supplyAsync-3 start \n", Thread.currentThread().getName());
                        Thread.sleep(1000);
                        System.out.printf("[%s] supplyAsync-3 end \n", Thread.currentThread().getName());
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    return " haha~";
                })
        };

        CompletableFuture.allOf(futures).thenAccept(s -> {
            String result = Arrays.asList(futures).stream()
                    .map(f -> f.join().toString())
                    .collect(Collectors.joining(""));

            System.out.printf("[%s] thenAccept-1 : %s \n", Thread.currentThread().getName(), result);
        });

        System.out.printf("[%s] main end \n", Thread.currentThread().getName());

        Thread.sleep(5000); // 5초 뒤 프로그램 종료

        /*
        [main] main start
        [ForkJoinPool.commonPool-worker-5] supplyAsync-2 start
        [ForkJoinPool.commonPool-worker-3] supplyAsync-1 start
        [ForkJoinPool.commonPool-worker-7] supplyAsync-3 start
        [main] main end
        [ForkJoinPool.commonPool-worker-3] supplyAsync-1 end
        [ForkJoinPool.commonPool-worker-5] supplyAsync-2 end
        [ForkJoinPool.commonPool-worker-7] supplyAsync-3 end
        [ForkJoinPool.commonPool-worker-7] thenAccept-1 : hello, world. haha~
         */
    }
}
