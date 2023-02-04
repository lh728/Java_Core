package com_second.Input_and_output;

import java.io.*;
import java.time.LocalDate;

public class SerialCloneTets {
    public static void main(String[] args) throws CloneNotSupportedException {
        Employee a = new Employee("A", 35000, 1989, 10, 1);
        var b = (Employee)a.clone();

        //mutate a
        a.raiseSalary(50);

        System.out.println(a);
        System.out.println(b);
    }
}

/**
 * A class whose clone method uses serialization
 */
class SerialCloneable implements Cloneable, Serializable{
    public Object clone() throws CloneNotSupportedException {
        try{
            // save the object to a byte array
            var bout = new ByteArrayOutputStream();
            try(var out = new ObjectOutputStream(bout)){
                out.writeObject(this);
            }

            // read a clone of the object from the byte array
            try (var bin = new ByteArrayInputStream(bout.toByteArray())){
                var in = new ObjectInputStream(bin);
                return in.readObject();
            }
        } catch (IOException | ClassNotFoundException e) {
            CloneNotSupportedException cloneNotSupportedException = new CloneNotSupportedException();
            cloneNotSupportedException.initCause(e);
            throw cloneNotSupportedException;
        }
    }
}

/**
 * The familiar employee class, redefined to extend the SerialCloneable class
 */
class Employee extends SerialCloneable{
    private String name;
    private double salary;
    private LocalDate hireDay;

    public Employee(String name, double salary, int year,int month,int day) {
        this.name = name;
        this.salary = salary;
        this.hireDay = LocalDate.of(year,month,day);
    }

    public String getName() {
        return name;
    }

    public double getSalary() {
        return salary;
    }

    public LocalDate getHireDay() {
        return hireDay;
    }

    /**
     * Raises the salary of this employee
     * @byPercent the percentage of the raise
     */
    public void raiseSalary(double byPercent){
        double raise = salary * byPercent / 100;
        salary += raise;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "name='" + name + '\'' +
                ", salary=" + salary +
                ", hireDay=" + hireDay +
                '}';
    }
}


