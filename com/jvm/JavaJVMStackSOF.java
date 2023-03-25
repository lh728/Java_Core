package com.company.jvm;

/*
VM args: -Xss180k
 */
public class JavaJVMStackSOF {
    private int stackLength = 1;
    public void stackLeak(){
        stackLength++;
        stackLeak();
    }

    public static void main(String[] args) throws Throwable {
        JavaJVMStackSOF oom = new JavaJVMStackSOF();
        try{
            oom.stackLeak();
        }catch (Throwable e){
            System.out.println("length: " + oom.stackLength);
            throw e;
        }
    }

}
