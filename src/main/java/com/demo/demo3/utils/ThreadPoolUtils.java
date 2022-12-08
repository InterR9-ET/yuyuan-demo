package com.demo.demo3.utils;

import lombok.extern.log4j.Log4j2;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

@Log4j2
public class ThreadPoolUtils {
    static List<String> strList = new ArrayList<>(3);
    static CountDownLatch latch = new CountDownLatch(3);
    static String jewellery;
    static String food;
    static String clothing;
    public static void sendCard() throws InterruptedException {
        jewellery="jewellery";
        food="food";
        clothing="clothing";
        ThreadPoolExecutor executor = new ThreadPoolExecutor(3,
                6,
                10000,
                TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(10),
                Executors.defaultThreadFactory(),
                new ThreadPoolExecutor.DiscardPolicy());

        ThreadPoolUtils pool = new ThreadPoolUtils();
        long time1 = System.currentTimeMillis();
        System.out.println(time1);
        executor.execute(pool::sendJewellery);
        executor.execute(pool::sendFood);
        executor.execute(pool::sendClothing);
        latch.await();
        long time2 = System.currentTimeMillis();
        System.out.println(time2);
        System.out.println(time2 - time1);
        System.out.println(strList.toString());
    }
    private void sendJewellery(){
        try {
            TimeUnit.SECONDS.sleep(5);
            System.out.println(Thread.currentThread().getName() + "珠宝 - 开始执行"+jewellery);
            strList.add(Thread.currentThread().getName());
            latch.countDown();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    private void sendFood(){
        try {
            TimeUnit.SECONDS.sleep(5);
            System.out.println(Thread.currentThread().getName() + "食品 - 开始执行"+food);
            strList.add(Thread.currentThread().getName());
            latch.countDown();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    private void sendClothing(){
        try {
            TimeUnit.SECONDS.sleep(5);
            System.out.println(Thread.currentThread().getName() + "服装 - 开始执行"+clothing);
            strList.add(Thread.currentThread().getName());
            latch.countDown();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}