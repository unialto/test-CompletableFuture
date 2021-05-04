package com.unialto.test.completableFuture;

import java.util.concurrent.CompletableFuture;

/**
 * CompletableFuture.thenCombine
 * <p>
 * supplyAsync >
 * supplyAsync > thenAccept
 * </p>
 */
public class Test5 {
    public static void main(String[] args) throws Exception {
        System.out.printf("[%s] main start \n", Thread.currentThread().getName());

        CompletableFuture.supplyAsync(() -> {
            try {
                System.out.printf("[%s] supplyAsync-1 start \n", Thread.currentThread().getName());
                Thread.sleep(500);
                System.out.printf("[%s] supplyAsync-1 end \n", Thread.currentThread().getName());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            return "hello,";
        }).thenCombine(
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
                (s1, s2) -> s1 + s2
        ).thenAccept(s -> {
            System.out.printf("[%s] thenAccept-1 : %s \n", Thread.currentThread().getName(), s);
        });

        System.out.printf("[%s] main end \n", Thread.currentThread().getName());

        Thread.sleep(5000); // 5초 뒤 프로그램 종료

        /*
        [main] main start
        [ForkJoinPool.commonPool-worker-5] supplyAsync-2 start
        [ForkJoinPool.commonPool-worker-3] supplyAsync-1 start
        [main] main end
        [ForkJoinPool.commonPool-worker-3] supplyAsync-1 end
        [ForkJoinPool.commonPool-worker-5] supplyAsync-2 end
        [ForkJoinPool.commonPool-worker-5] thenAccept-1 : hello, world.
         */
    }
}
