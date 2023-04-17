package net.javaguides.spring.ioc;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class JConsole {
    public static void main(String[] args) throws IOException {
        BufferedReader cr = new BufferedReader(new InputStreamReader(System.in));
        cr.readLine();
        createBusyThread();
        cr.readLine();
        Object o = new Object();
        createLockThread(o);
    }

    private static void createLockThread(final Object o) {
        Thread testLockThread = new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (o) {
                    try {
                        o.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, "testLockThread");
        testLockThread.start();

    }

    private static void createBusyThread() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true)
                    ;
            }
        },"testBusyThread");
        thread.start();
    }
}
