package com.zhiwen.synchronizedTest;

import java.util.concurrent.CountDownLatch;

/**
 * @author wangzhiwen
 * @version 1.0
 * @description Synchronized用法
 * @date 2020/8/10 19:57
 **/
public class SynchronizedTest {
    // 共享变量
    private static int count = 0;

    private static final CountDownLatch countDownLatch = new CountDownLatch(10);

    public synchronized void add() {
        // 自加10000次
        for (int i = 0; i < 10000; i++) {
            count++;
        }
    }
    public static void main(String[] args) throws InterruptedException {
        for (int i = 0; i < 10; i++) {
            new Thread(new Runnable() {
                public void run() {
                    new SynchronizedTest().add();
                    countDownLatch.countDown();
                }
            }).start();
        }
        // 只有10个线程全部执行完(countDownLatch.getCount() == 0)才会唤醒主线程继续执行
        countDownLatch.await();
        System.out.print(count);
    }
}
