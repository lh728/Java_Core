package com_second.DataBase;

import java.sql.Time;
import java.util.Date;

public class SqlDateTime {
    public static void main(String[] args) {
        Date javaDate = new Date();
        long time = javaDate.getTime();
        System.out.println(javaDate.toString());

        java.sql.Date sqlDate = new java.sql.Date(time);
        System.out.println(sqlDate.toString());

        Time time1 = new Time(time);
        System.out.println(time1);

        java.sql.Timestamp sqlTimestamp =
                new java.sql.Timestamp(time);
        System.out.println("The SQL TIMESTAMP is: " +
                sqlTimestamp.toString());

    }
}
