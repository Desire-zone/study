package com.zhiwen.synchronizedTest;

import org.openjdk.jol.info.ClassLayout;

/**
 * @author ：杨过
 * @date ：Created in 2020/8/9
 * @version: V1.0
 * @slogan: 天下风云出我辈，一入代码岁月催
 * @description:
 **/

public class T0_BasicLock {
    public static void main(String[] args) {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Object o = new Object();
        System.out.println((ClassLayout.parseInstance(o).toPrintable()));

        new Thread(()->{
            synchronized (o){
                System.out.println((ClassLayout.parseInstance(o).toPrintable()));
            }
        }).start();

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println((ClassLayout.parseInstance(o).toPrintable()));
        new Thread(()->{
            synchronized (o){
                System.out.println((ClassLayout.parseInstance(o).toPrintable()));
            }
        }).start();
    }
}
