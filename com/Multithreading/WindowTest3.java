package com_second.Multithreading;

/**
 * @author lhj
 * @create 2023-3-1
 */
public class WindowTest3 {
    public static void main(String[] args) {
        Window3 window3 = new Window3();
        Thread thread = new Thread(window3);
        Thread thread2 = new Thread(window3);
        Thread thread3 = new Thread(window3);

        thread.setName("window1");
        thread2.setName("window2");
        thread3.setName("window3");

        thread.start();
        thread2.start();
        thread3.start();
    }
}

class Window3 implements Runnable {
    private int ticket = 100;
//    private  Object obj = new Object();

    @Override

    public void run() {

        while (true) {
            show();

        }
    }

    private synchronized void show() {
        if (ticket > 0) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + ":Now the ticket is " + ticket);
            ticket--;
        }
    }


}
