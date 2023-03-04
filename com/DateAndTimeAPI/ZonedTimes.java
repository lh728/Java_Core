package com_second.DataAndTimeAPI;

import java.time.*;

public class ZonedTimes {
    public static void main(String[] args) {
        ZonedDateTime apollolllaunch = ZonedDateTime.of(1969, 7, 16, 9, 32, 0, 0, ZoneId.of("America/New_York"));
        System.out.println("apollolllaunch: " + apollolllaunch);

        Instant instant = apollolllaunch.toInstant();
        System.out.println("instant: " + instant);

        ZonedDateTime utc = instant.atZone(ZoneId.of("UTC"));
        System.out.println("utc: " + utc);

        ZonedDateTime skipped = ZonedDateTime.of(LocalDate.of(2013, 3, 31), LocalTime.of(2, 30),
                ZoneId.of("Europe/Berlin"));
        System.out.println("skipped: " + skipped);

        ZonedDateTime ambiguous = ZonedDateTime.of(LocalDate.of(2013, 10, 27), LocalTime.of(2, 30),
                ZoneId.of("Europe/Berlin"));
        ZonedDateTime anHourLater = ambiguous.plusHours(1);
        System.out.println("ambiguous: " + ambiguous);
        System.out.println("anHourLater: " + anHourLater);

        ZonedDateTime meeting = ZonedDateTime.of(LocalDate.of(2013, 10, 31), LocalTime.of(14, 30), ZoneId.of("America/Los_Angeles"));
        System.out.println("meeting: " + meeting);
        ZonedDateTime nextmeeting = meeting.plus(Duration.ofDays(7));
        ZonedDateTime nextmeeting2 = meeting.plus(Period.ofDays(7));
        System.out.println("nextMeeting: " + nextmeeting);
        System.out.println("nextMeeting2: " + nextmeeting2);

    }
}
