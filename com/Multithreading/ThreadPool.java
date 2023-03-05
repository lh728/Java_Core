package com_second.Multithreading;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class ThreadPool {
    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        ThreadPoolExecutor service1 = (ThreadPoolExecutor) executorService;
//        service1.setCorePoolSize(15);

        executorService.execute(new NumberThread());
        executorService.execute(new NumberThread1());
        executorService.shutdown();


    }
}
class NumberThread implements Runnable{
    @Override
    public void run() {
        for (int i = 0; i < 100; i++) {
            if (i %2 == 0)
                System.out.println(Thread.currentThread().getName() + ":" + i);
        }
    }
}
class NumberThread1 implements Runnable{
    @Override
    public void run() {
        for (int i = 0; i < 100; i++) {
            if (i %2 != 0)
                System.out.println(Thread.currentThread().getName() + ":" + i);
        }
    }
}

