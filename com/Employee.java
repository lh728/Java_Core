package com;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Random;

public class Employee extends Person implements Serializable {
    /** 员工类
     * <strong> 想想这是干嘛的 </strong>
     * @author hongjin
     * @version 8
     * {@link demo2#getType}
     */
//    private String name = "";
    private double salary;
    private LocalDate hireDay;

    private  static int nextId;
    private int id;
    private Employee secretary;

    static
    {
        Random generator = new Random();
        nextId = generator.nextInt(10000);
    }

    {
        id = nextId;
        nextId++;
    }

    public Employee() {

    }

    public Employee(String name, double salary, int year, int month, int day){
        super(name);
        this.salary = salary;
        hireDay = LocalDate.of(year,month,day);
//        id = 0;
    }

    public Employee(String name) {
        super(name);
    }
    //    public Employee(double salary){
//        this("Employee #" + nextId,salary);
//    }

//    public String getName() {
//        return name;
//    }

    @Override
    public String getDescription() {
        return String.format("an employee with a salary of $%.2f",salary);
    }

    public double getSalary() {
        return salary;
    }

    public LocalDate getHireDay() {
        return hireDay;
    }
    public void raiseSalary(double byPercent){
        double raise = salary * byPercent / 100;
        salary += raise;
    }

    public int getId() {
        return id;
    }

    public static int getNextId() {
        return nextId;
    }

    public void setId() {
        id = nextId;
        nextId++;
    }

//    public static void main(String[] args) {
//        Employee e = new Employee("Harry",5000);
//        System.out.println(e.getName() + " " + e.getSalary());
//    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Employee employee = (Employee) o;

        if (Double.compare(employee.salary, salary) != 0) return false;
//        if (id != employee.id) return false;
        return hireDay != null ? hireDay.equals(employee.hireDay) : employee.hireDay == null;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        temp = Double.doubleToLongBits(salary);
        result = (int) (temp ^ (temp >>> 32));
        result = 31 * result + (hireDay != null ? hireDay.hashCode() : 0);
//        result = 31 * result + id;
        return result;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "salary=" + salary +
                ", hireDay=" + hireDay +
                ", id=" + id +
                '}';
    }

    public void setSecretary(Employee secretary) {
        this.secretary = secretary;
    }
}
