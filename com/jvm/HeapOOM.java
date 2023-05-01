package jvm;

import java.util.ArrayList;  


/*
-Xms20m
-Xmx20m
-XX:+HeapDumpOnOutOf-MemoryError 
 */
 
public class HeapOOM { 
    static class OOMObject{}

    public static void main(String[] args) {
        ArrayList<OOMObject> list = new ArrayList<>();
        while (true){
            list.add(new OOMObject());
        }
    } 
}
