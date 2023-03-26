package com;

import java.util.HashSet;

/*
Only JDK6, VM args: -XX:PermSize=6M -XX:MaxPermSize=6M
JDK6 later, needs VM args: -Xmx limit
 */
public class RuntimeConstantPoolOOM {
    public static void main(String[] args) {
        HashSet<String> set = new HashSet<>();
        short i = 0;
        while (true){
            set.add(String.valueOf(i++).intern());
        }
    }
}
