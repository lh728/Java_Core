package com.company.jvm;


/*
Windows pay attention to run, because it will kill ur computer
 */

public class JavaVMStacjOOM {
    private void dontstop(){
        while (true){

        }
    }

    public void stackLeakByThread(){
        while (true){
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    dontstop();
                }
            });
            thread.start();
        }
    }

    public static void main(String[] args) {
        JavaVMStacjOOM oom = new JavaVMStacjOOM();
        oom.stackLeakByThread();
    }
}
