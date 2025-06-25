package com.ljt.study.lang.jdk.jdk8;

import java.time.*;

/**
 * @author LiJingTang
 * @date 2019-12-29 14:05
 */
public class DateTest {

    public static void main(String[] args) {
        Clock clock = Clock.systemUTC();
        System.out.println(clock.instant());
        System.out.println(clock.millis());

        LocalDate date = LocalDate.now();
        LocalDate dateFromClock = LocalDate.now(clock);
        System.out.println(date);
        System.out.println(dateFromClock);

        // Get the local date and local time
        LocalTime time = LocalTime.now();
        LocalTime timeFromClock = LocalTime.now(clock);
        System.out.println(time);
        System.out.println(timeFromClock);

        LocalDateTime datetime = LocalDateTime.now();
        LocalDateTime datetimeFromClock = LocalDateTime.now(clock);
        System.out.println(datetime);
        System.out.println(datetimeFromClock);

        // Get the zoned date/time
        ZonedDateTime zonedDatetime = ZonedDateTime.now();
        ZonedDateTime zonedDatetimeFromClock = ZonedDateTime.now(clock);
        ZonedDateTime zonedDatetimeFromZone = ZonedDateTime.now(ZoneId.of("America/Los_Angeles"));
        System.out.println(zonedDatetime);
        System.out.println(zonedDatetimeFromClock);
        System.out.println(zonedDatetimeFromZone);

        LocalDateTime from = LocalDateTime.of(2014, Month.APRIL, 16, 0, 0, 0);
        LocalDateTime to = LocalDateTime.of(2015, Month.APRIL, 16, 23, 59, 59);
        Duration duration = Duration.between(from, to);
        System.out.println("Duration in days: " + duration.toDays());
        System.out.println("Duration in hours: " + duration.toHours());
    }

}
