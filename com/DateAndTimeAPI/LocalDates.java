package com_second.DataAndTimeAPI;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.time.Period;
import java.time.temporal.ChronoUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class LocalDates {
    public static void main(String[] args) {
        LocalDate today = LocalDate.now();
        System.out.println("today: " + today);

        LocalDate alonzosBirthday = LocalDate.of(1903, 6, 14);
        alonzosBirthday = LocalDate.of(1903, Month.JUNE, 14);
        System.out.println("alonzosBirthday: " + alonzosBirthday);

        LocalDate programmersDay = LocalDate.of(2018, 1, 1).plusDays(255);
        System.out.println("programmersDay: " + programmersDay);

        LocalDate independenceDay = LocalDate.of(2018, Month.JULY, 4);
        LocalDate Christmas = LocalDate.of(2018, Month.DECEMBER, 25);

        System.out.println("Until Christmas: " + independenceDay.until(Christmas));
        System.out.println("Until christmas: " + independenceDay.until(Christmas, ChronoUnit.DAYS));

        System.out.println(LocalDate.of(2016,1,31).plusMonths(1));
        System.out.println(LocalDate.of(2016,3,31).minusMonths(1));

        DayOfWeek dayOfWeek = LocalDate.of(1900, 1, 1).getDayOfWeek();
        System.out.println("dayOfWeek: " + dayOfWeek);
        System.out.println(dayOfWeek.getValue());
        System.out.println(DayOfWeek.SATURDAY.plus(3));

        LocalDate start = LocalDate.of(2000, 1, 1);
        LocalDate end = LocalDate.now();
        Stream<LocalDate> localDateStream = start.datesUntil(end, Period.ofMonths(1));
        System.out.println("firstDayIn month: "+ localDateStream.collect(Collectors.toList()));
    }
}
