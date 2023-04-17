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

class SynAddRunnable implements Runnable{
    int a,b;

    public SynAddRunnable(int a, int b) {
        this.a = a;
        this.b = b;
    }

    @Override
    public void run() {
        synchronized (Integer.valueOf(a)){
            synchronized (Integer.valueOf(b)){
                System.out.println(a + b);
            }
        }
    }

    public static void main(String[] args) {
        for (int i = 0; i < 100; i++) {
            new Thread(new SynAddRunnable(1,2)).start();
            new Thread(new SynAddRunnable(2,1)).start();
        }
    }
}
