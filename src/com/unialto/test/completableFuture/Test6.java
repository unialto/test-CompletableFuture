package com.unialto.test.completableFuture;

import java.util.concurrent.CompletableFuture;

/**
 * CompletableFuture.anyOf
 * <p>
 * supplyAsync start > ................................. > supplyAsync end
 * supplyAsync start > ............... > supplyAsync end
 * supplyAsync start > supplyAsync end > thenAccept
 * </p>
 */
public class Test6 {
    public static void main(String[] args) throws Exception {
        System.out.printf("[%s] main start \n", Thread.currentThread().getName());

        CompletableFuture.anyOf(
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
                        Thread.sleep(500);
                        System.out.printf("[%s] supplyAsync-2 end \n", Thread.currentThread().getName());
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    return " world.";
                }),
                CompletableFuture.supplyAsync(() -> {
                    try {
                        System.out.printf("[%s] supplyAsync-3 start \n", Thread.currentThread().getName());
                        Thread.sleep(100);
                        System.out.printf("[%s] supplyAsync-3 end \n", Thread.currentThread().getName());
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    return " haha~";
                })
        ).thenAccept(s -> {
            System.out.printf("[%s] thenAccept-1 : %s \n", Thread.currentThread().getName(), s);
        });

        System.out.printf("[%s] main end \n", Thread.currentThread().getName());

        Thread.sleep(5000); // 5초 뒤 프로그램 종료

        /*
        [main] main start
        [ForkJoinPool.commonPool-worker-5] supplyAsync-2 start
        [ForkJoinPool.commonPool-worker-3] supplyAsync-1 start
        [ForkJoinPool.commonPool-worker-7] supplyAsync-3 start
        [main] main end
        [ForkJoinPool.commonPool-worker-7] supplyAsync-3 end
        [ForkJoinPool.commonPool-worker-7] thenAccept-1 :  haha~
        [ForkJoinPool.commonPool-worker-5] supplyAsync-2 end
        [ForkJoinPool.commonPool-worker-3] supplyAsync-1 end
         */
    }
}
