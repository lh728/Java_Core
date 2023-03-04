package com_second.DataAndTimeAPI;

import java.text.DateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.time.format.TextStyle;
import java.util.Locale;

public class Formatting {
    public static void main(String[] args) {
        ZonedDateTime apollp11launch = ZonedDateTime.of(1969, 7, 16, 9, 32, 0, 0, ZoneId.of("America/New_York"));
        System.out.println(apollp11launch);
        String format = DateTimeFormatter.ISO_OFFSET_DATE_TIME.format(apollp11launch);
        System.out.println(format);

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.LONG);
        format = dateTimeFormatter.format(apollp11launch);
        System.out.println(format);

        format = dateTimeFormatter.withLocale(Locale.FRENCH).format(apollp11launch);
        System.out.println(format);

        dateTimeFormatter = DateTimeFormatter.ofPattern("E yyyy-MM-dd HH:mm");
        format = dateTimeFormatter.format(apollp11launch);
        System.out.println(format);

        LocalDate churchsBirthday = LocalDate.parse("1903-06-14");
        System.out.println("churchsBirthday: " + churchsBirthday);
        apollp11launch = ZonedDateTime.parse("1967-07-16 03:32:00-0400", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ssxx"));
        System.out.println("apollp11launch: " + apollp11launch);

        for (DayOfWeek w : DayOfWeek.values()){
            System.out.println(w.getDisplayName(TextStyle.SHORT,Locale.CHINA) + " ");
        }

    }
}
