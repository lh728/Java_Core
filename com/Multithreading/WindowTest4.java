package com_second.Multithreading;

/**
 * @author lhj
 * @create 2023-3-2
 */
public class WindowTest4 {
    public static void main(String[] args) {
        Thread thread = new Window4();
        Thread thread2 = new Window4();
        Thread thread3 = new Window4();

        thread.setName("window1");
        thread2.setName("window2");
        thread3.setName("window3");

        thread.start();
        thread2.start();
        thread3.start();
    }
}

class Window4 extends Thread {
    private static int ticket = 100;

    @Override

    public void run() {

        while (true) {
            show();

        }
    }

    public static synchronized void show(){
        // no static is not safe
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
