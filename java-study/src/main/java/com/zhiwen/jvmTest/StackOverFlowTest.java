package com.zhiwen.jvmTest;

/**
 * @author wangzhiwen
 * @version 1.0
 * @description
 * @date 2020/8/19 10:55
 **/
public class StackOverFlowTest {
    static int count = 0;

    static void redo() {
        count++;
        redo();
    }

    public static void main(String[] args) {
        try {
            redo();
        } catch (Throwable t) {
            t.printStackTrace();
            System.out.println(count);
        }
    }
}
