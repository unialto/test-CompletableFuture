package com.unialto.test.completableFuture;

import java.util.concurrent.CompletableFuture;

/**
 * CompletableFuture.get and thenAccept
 * <p>
 * CompletableFuture.runAsync(...).get(); // Blocking
 * CompletableFuture.runAsync(...).thenAccept(...);
 * </p>
 */
public class Test1 {
    public static void main(String[] args) throws Exception {
        System.out.printf("[%s] main start \n", Thread.currentThread().getName());

        CompletableFuture.runAsync(() -> {
            try {
                System.out.printf("[%s] runAsync-1 start \n", Thread.currentThread().getName());
                Thread.sleep(1000);
                System.out.printf("[%s] runAsync-1 end \n", Thread.currentThread().getName());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).thenAccept(s -> {
            System.out.printf("[%s] runAsync-1.thenAccept \n", Thread.currentThread().getName());
        });

        CompletableFuture.runAsync(() -> {
            try {
                System.out.printf("[%s] runAsync-2 start \n", Thread.currentThread().getName());
                Thread.sleep(1000);
                System.out.printf("[%s] runAsync-2 end \n", Thread.currentThread().getName());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).get(); // Blocking

        CompletableFuture.runAsync(() -> {
            try {
                System.out.printf("[%s] runAsync-3 start \n", Thread.currentThread().getName());
                Thread.sleep(1000);
                System.out.printf("[%s] runAsync-3 end \n", Thread.currentThread().getName());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).thenAccept(s -> {
            System.out.printf("[%s] runAsync-3.thenAccept \n", Thread.currentThread().getName());
        });

        System.out.printf("[%s] main end \n", Thread.currentThread().getName());

        Thread.sleep(5000); // 5초 뒤 프로그램 종료

        /*
        [main] main start
        [ForkJoinPool.commonPool-worker-3] runAsync-1 start
        [ForkJoinPool.commonPool-worker-5] runAsync-2 start
        [ForkJoinPool.commonPool-worker-5] runAsync-2 end
        [ForkJoinPool.commonPool-worker-3] runAsync-1 end
        [ForkJoinPool.commonPool-worker-3] runAsync-1.thenAccept
        [ForkJoinPool.commonPool-worker-3] runAsync-3 start
        [main] main end
        [ForkJoinPool.commonPool-worker-3] runAsync-3 end
        [ForkJoinPool.commonPool-worker-3] runAsync-3.thenAccept
         */
    }
}
