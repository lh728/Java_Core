package com_second.Input_and_output;

import com.Employee;
import com.Manager;

import java.io.*;

public class ObjectStreamTest {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        Employee a = new Employee("a", 50000, 1989, 10, 1);
        Manager b = new Manager("b", 80000, 1989, 10, 1);
        b.setSecretary(a);
        Manager c = new Manager("c", 40000, 1990, 3, 15);
        c.setSecretary(a);

        var staff = new Employee[3];
        staff[0] = a;
        staff[1] = b;
        staff[2] = c;

        try(var out = new ObjectOutputStream(new FileOutputStream("employee.dat"))){
            out.writeObject(staff);
        }

        try(var in = new ObjectInputStream(new FileInputStream("employee.dat"))){
            var newStaff = (Employee[]) in.readObject();
            newStaff[1].raiseSalary(10);
            for (Employee e: newStaff){
                System.out.println(e);
            }
        }

    }
}
