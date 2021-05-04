package com.unialto.test.completableFuture;

import java.util.concurrent.CompletableFuture;

/**
 * CompletableFuture.thenCompose
 * <p>
 * supplyAsync > supplyAsync > thenAccept
 * </p>
 */
public class Test4 {
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

            return "hello,";
        }).thenCompose(s -> {
            System.out.printf("[%s] thenCompose-1 : %s \n", Thread.currentThread().getName(), s);

            return CompletableFuture.supplyAsync(() -> {
                try {
                    System.out.printf("[%s] supplyAsync-2 start \n", Thread.currentThread().getName());
                    Thread.sleep(500);
                    System.out.printf("[%s] supplyAsync-2 end \n", Thread.currentThread().getName());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                return s + " world.";
            });
        }).thenCompose(s -> {
            System.out.printf("[%s] thenCompose-2 : %s \n", Thread.currentThread().getName(), s);

            return CompletableFuture.supplyAsync(() -> {
                try {
                    System.out.printf("[%s] supplyAsync-3 start \n", Thread.currentThread().getName());
                    Thread.sleep(100);
                    System.out.printf("[%s] supplyAsync-3 end \n", Thread.currentThread().getName());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                return s + " haha~";
            });
        }).thenAccept(s -> {
            System.out.printf("[%s] thenAccept-1 : %s \n", Thread.currentThread().getName(), s);
        });

        System.out.printf("[%s] main end \n", Thread.currentThread().getName());

        Thread.sleep(5000); // 5초 뒤 프로그램 종료

        /*
        [main] main start
        [ForkJoinPool.commonPool-worker-3] supplyAsync-1 start
        [main] main end
        [ForkJoinPool.commonPool-worker-3] supplyAsync-1 end
        [ForkJoinPool.commonPool-worker-3] thenCompose-1 : hello,
        [ForkJoinPool.commonPool-worker-5] supplyAsync-2 start
        [ForkJoinPool.commonPool-worker-5] supplyAsync-2 end
        [ForkJoinPool.commonPool-worker-5] thenCompose-2 : hello, world.
        [ForkJoinPool.commonPool-worker-5] supplyAsync-3 start
        [ForkJoinPool.commonPool-worker-5] supplyAsync-3 end
        [ForkJoinPool.commonPool-worker-5] thenAccept-1 : hello, world. haha~
         */
    }
}
