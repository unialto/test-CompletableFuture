package com.unialto.test.completableFuture;

import java.util.concurrent.CompletableFuture;

/**
 * CompletableFuture.thenAccept and thenApply
 * <p>
 * supplyAsync > thenAccept > thenApply
 * </p>
 */
public class Test3 {
    public static void main(String[] args) throws Exception {
        System.out.printf("[%s] main start \n", Thread.currentThread().getName());

        CompletableFuture.supplyAsync(() -> {
            try {
                System.out.printf("[%s] supplyAsync-1 start \n", Thread.currentThread().getName());
                Thread.sleep(1000);
                System.out.printf("[%s] supplyAsync-1 end \n", Thread.currentThread().getName());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            return "1";
        }).thenAccept(s -> {
            try {
                Thread.sleep(500);
                System.out.printf("[%s] thenAccept-1 : %s \n", Thread.currentThread().getName(), s);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).thenApply(s -> {
            try {
                Thread.sleep(100);
                System.out.printf("[%s] thenApply-1 : %s \n", Thread.currentThread().getName(), s);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            return "2";
        }).thenAccept(s -> {
            System.out.printf("[%s] thenAccept-2 : %s \n", Thread.currentThread().getName(), s);
        });

        System.out.printf("[%s] main end \n", Thread.currentThread().getName());

        Thread.sleep(5000); // 5초 뒤 프로그램 종료

        /*
        [main] main start
        [ForkJoinPool.commonPool-worker-3] supplyAsync-1 start
        [main] main end
        [ForkJoinPool.commonPool-worker-3] supplyAsync-1 end
        [ForkJoinPool.commonPool-worker-3] thenAccept-1 : 1
        [ForkJoinPool.commonPool-worker-3] thenApply-1 : null
        [ForkJoinPool.commonPool-worker-3] thenAccept-2 : 2
         */
    }
}
