package com.unialto.test.completableFuture;

import java.util.concurrent.CompletableFuture;

/**
 * CompletableFuture.runAsync and supplyAsync
 * <p>
 * CompletableFuture.supplyAsync(() -> { ... return s; }).get(); -> s
 * CompletableFuture.supplyAsync(() -> { ... return s; }).thenAccept((s) -> s);
 * CompletableFuture.runAsync(...).thenAccept((s) -> null);
 * </p>
 */
public class Test2 {
    public static void main(String[] args) throws Exception {
        System.out.printf("[%s] main start \n", Thread.currentThread().getName());

        String result1 = CompletableFuture.supplyAsync(() -> {
            try {
                System.out.printf("[%s] supplyAsync-1 start \n", Thread.currentThread().getName());
                Thread.sleep(1000);
                System.out.printf("[%s] supplyAsync-1 end \n", Thread.currentThread().getName());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            return "1";
        }).get();

        System.out.printf("[%s] runAsync-1.get : %s \n", Thread.currentThread().getName(), result1);

        CompletableFuture.supplyAsync(() -> {
            try {
                System.out.printf("[%s] supplyAsync-2 start \n", Thread.currentThread().getName());
                Thread.sleep(1000);
                System.out.printf("[%s] supplyAsync-2 end \n", Thread.currentThread().getName());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            return "2";
        }).thenAccept(s -> {
            System.out.printf("[%s] supplyAsync-2.thenAccept : %s \n", Thread.currentThread().getName(), s);
        });

        CompletableFuture.runAsync(() -> {
            try {
                System.out.printf("[%s] runAsync-3 start \n", Thread.currentThread().getName());
                Thread.sleep(1000);
                System.out.printf("[%s] runAsync-3 end \n", Thread.currentThread().getName());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).thenAccept(s -> {
            System.out.printf("[%s] runAsync-3.thenAccept : %s \n", Thread.currentThread().getName(), s);
        });

        System.out.printf("[%s] main end \n", Thread.currentThread().getName());

        Thread.sleep(5000); // 5초 뒤 프로그램 종료

        /*
        [main] main start
        [ForkJoinPool.commonPool-worker-3] supplyAsync-1 start
        [ForkJoinPool.commonPool-worker-3] supplyAsync-1 end
        [main] runAsync-1.get : 1
        [ForkJoinPool.commonPool-worker-3] supplyAsync-2 start
        [ForkJoinPool.commonPool-worker-5] runAsync-3 start
        [main] main end
        [ForkJoinPool.commonPool-worker-3] supplyAsync-2 end
        [ForkJoinPool.commonPool-worker-5] runAsync-3 end
        [ForkJoinPool.commonPool-worker-3] supplyAsync-2.thenAccept : 2
        [ForkJoinPool.commonPool-worker-5] runAsync-3.thenAccept : null
         */
    }
}
