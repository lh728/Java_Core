package net.javaguides.spring.ioc;

/*
java -XX:+PrintAssembly -Xcomp -XX:CompileCommand=dontinline,*Bar.sum
-XX:CompileCommand=compileonly,*Bar.sum test.Bar 
 */ 
public class Bar {
    int a = 1; 
    static int b = 1;
 
    public static void main(String[] args) {
        new Bar().sum(3);
    }

    private int sum(int c) { 
        return a + b + c;
    } 
} 
