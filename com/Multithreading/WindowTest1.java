package com_second.Multithreading;

/**
 * @author lhj
 * @create 2023-2-26
 */
public class WindowTest1{
    public static void main(String[] args) {
        Window1 window1 = new Window1();
        Thread thread = new Thread(window1);
        Thread thread2 = new Thread(window1);
        Thread thread3 = new Thread(window1);

        thread.setName("window1");
        thread2.setName("window2");
        thread3.setName("window3");

        thread.start();
        thread2.start();
        thread3.start();
    }
}
class Window1 implements Runnable{
    private int ticket = 100;
//    private  Object obj = new Object();

    @Override
    public void run() {

        while (true ){
            synchronized (this) {
                if (ticket > 0) {
//                    try {
//                        Thread.sleep(100);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
                    System.out.println(Thread.currentThread().getName() + ":Now the ticket is " + ticket);
                    ticket--;
                } else {
                    break;
                }
            }
        }
    }
}
