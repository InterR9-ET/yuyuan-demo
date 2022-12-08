package com.demo.demo3.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class BatchTest {

    private static int count=0;
    //线程数
    /**
     * @param args
     */

    public static void main(String[] args) {
        while (true) {
            if (count == 1) {
                System.out.println("线程名字:" + Thread.currentThread().getName() + " 线程id:" + Thread.currentThread().getId() + "执行结束");
                break;
            }
            //1.获取任务，设定10任务，待线程分配处理
            List<Integer> taskList = new ArrayList<Integer>();
            for (int k = 0; k < 2; k++) {
                System.out.println("线程名字:" + Thread.currentThread().getName() + " 线程id:" + Thread.currentThread().getId() + "查询集合");
                taskList.add(k);
            }
            //线程数
            int num = 2;
            //CountDownLatch是一个同步辅助类也可以使用AtomicInteger替代
            CountDownLatch doneSignal = new CountDownLatch(num);
            ExecutorService pool = Executors.newFixedThreadPool(num);
//            BatchUtils t =new BatchUtils();
            for (int k = 0; k < num; k++) {
                //在未来某个时间执行给定的命令
                System.out.println("线程名字:" + Thread.currentThread().getName() + " 线程id:" + Thread.currentThread().getId() + "开始执行");
//                List<Integer> list = doWork(k, taskList);
                System.out.println("线程名字:" + Thread.currentThread().getName() + " 线程id:" + Thread.currentThread().getId() + "获得任务集合");
                pool.execute(new Runnable() {

                    public void run() {
                        //子线程的任务
                        try {
                            //  System.out.println("线程:"+i+"休眠。。。。。");
                            System.out.println("线程名字:" + Thread.currentThread().getName() + " 线程id:" + Thread.currentThread().getId() + "休眠:");
                            Thread.sleep(1000);
//                            t.consumer(list);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        //任务执行完毕递减锁存器的计数
                        System.out.println("线程名字:" + Thread.currentThread().getName() + " 线程id:" + Thread.currentThread().getId() + "count前");
                        doneSignal.countDown();
                        System.out.println("线程名字:" + Thread.currentThread().getName() + " 线程id:" + Thread.currentThread().getId() + "count后");

                    }
                });
            }
            try {
                System.out.println("await前");
                doneSignal.await();
                System.out.println("await后");
            } catch (Exception e) {
                e.printStackTrace();
            }
            //子线程执行完毕，可以开始后续任务处理了
            System.out.println("所有任务执行完毕");
            count++;
            System.out.println("========");

        }
    }

    public  static  List<Integer> doWork(int i,List<Integer> taskList) {
        int totalSize = taskList.size();
        int ts = 5;
        if (ts > totalSize) {
            ts = totalSize;
        }
        int m = totalSize / ts;

        int startIndex = i * m;
        int endIndex = (i + 1) * m;
        if (i == ts - 1) {
            endIndex = totalSize;
        }
        List<Integer> tempIds = taskList.subList(startIndex, endIndex);
        return tempIds;
    }
    public void consumer(List<Integer> tempIds){
        for(int k=0;k<tempIds.size();k++){
            System.out.println("线程名字:"+Thread.currentThread().getName()+" 线程id:"+Thread.currentThread().getId()+"消费任务:"+tempIds.get(k));
        }
        System.out.println("线程名字:"+Thread.currentThread().getName()+" 线程id:"+Thread.currentThread().getId()+"本线程消费结束");
    }
}

