package com.zhiwen.classLoader;

import sun.misc.Launcher;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * @author wangzhiwen
 * @version 1.0
 * @description
 * @date 2020/8/18 09:25
 **/
public class ClassLoaderTest {
    public static void main(String[] args) {
        List<Integer> a = new ArrayList<Integer>();
        // 启动类加载器，为C++对象，java取不到，故为空
        System.out.println(String.class.getClassLoader());
        // 扩展类加载器
        System.out.println(com.sun.crypto.provider.DESKeyFactory.class.getClassLoader().getClass().getName());
        // 应用程序类加载器
        System.out.println(ClassLoaderTest.class.getClassLoader().getClass().getName());

        System.out.println();
        // 获取应用程序类加载器
        ClassLoader appClassLoader = ClassLoader.getSystemClassLoader();
        // 获取应用程序类加载器的父加载器 --> 扩展类加载器
        ClassLoader extClassloader = appClassLoader.getParent();
        // 获取扩展类加载器的父加载器 --> 启动类加载器
        ClassLoader bootstrapLoader = extClassloader.getParent();
        System.out.println("the bootstrapLoader : " + bootstrapLoader);
        System.out.println("the extClassloader : " + extClassloader);
        System.out.println("the appClassLoader : " + appClassLoader);

        System.out.println();
        System.out.println("bootstrapLoader加载以下文件：");
        // 获取启动类加载器的加载路径url
        URL[] urls = Launcher.getBootstrapClassPath().getURLs();
        for (int i = 0; i < urls.length; i++) {
            System.out.println(urls[i]);
        }

        System.out.println();
        System.out.println("extClassloader加载以下文件：");
        System.out.println(System.getProperty("java.ext.dirs"));

        System.out.println();
        System.out.println("appClassLoader加载以下文件：");
        System.out.println(System.getProperty("java.class.path"));

    }
}
