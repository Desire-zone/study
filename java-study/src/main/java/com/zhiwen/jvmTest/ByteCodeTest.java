package com.zhiwen.jvmTest;

/**
 * @author wangzhiwen
 * @version 1.0
 * @description
 * @date 2020/8/19 09:39
 **/
public class ByteCodeTest {

    public int compute() {  //一个方法对应一块栈帧内存区域
        // 将int类型常量1压入操作数栈(iconst_1), 将int类型常量1存入局部变量表(istore_1)
        int a = 1;
        // 将int类型常量2压入操作数栈(iconst_2), 将int类型常量2存入局部变量表(istore_2)
        int b = 2;
        // 从局部变量表装载int型变量1(iload_1)，从局部变量表装载int型变量2(iload_2)
        // 执行int类型加法(iadd)，将一个8位带符号整数压入栈(bipush)
        // 返回int型变量(ireturn)
        return (a + b) * 10;
    }

        public static void main(String[] args) {
            ByteCodeTest byteCodeTest = new ByteCodeTest();
            System.out.println(byteCodeTest.compute());
        }
}
