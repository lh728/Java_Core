package com_second.Multithreading;

import java.util.concurrent.locks.ReentrantLock;

public class LockTest {
    public static void main(String[] args) {
        Window w = new Window();
        Thread thread = new Thread(w);
        Thread thread2 = new Thread(w);
        Thread thread3 = new Thread(w);

        thread.setName("1 hao");
        thread2.setName("2 hao");
        thread3.setName("3 hao");


        thread.start();
        thread2.start();
        thread3.start();

    }
}

class Window implements Runnable {

    private int ticket = 100;

    private ReentrantLock lock = new ReentrantLock();

    @Override
    public void run() {
        while (true) {
            try {
                lock.lock();// run lock method
                if (ticket > 0) {

                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }

                    System.out.println(Thread.currentThread().getName() + ":sell ticket and ticket is : " + ticket);
                    ticket--;
                } else {
                    break;
                }
            }finally {
                lock.unlock();
            }
        }
    }
}


