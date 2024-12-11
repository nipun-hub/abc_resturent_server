package com.project.abc.commons;

import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class DateHelper {

    public enum Type {
        HOURS, DAYS, MINUTES, SECONDS
    }

    public static Date nowAsDate() {
        return new Date();
    }

    public static LocalDateTime now() {
        return LocalDateTime.now();
    }

    public static LocalDate asDate(Date date) {
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }

    public static LocalDate asDate(String date) {
        if(date == null) return null;
        return LocalDate.parse(date);
    }

    public static LocalTime asTime(String time) {
        return LocalTime.parse(time);
    }

    public static LocalDateTime asDateTime(Date date) {
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
    }

    public static LocalTime asTime(Date date) {
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalTime();
    }

    public static Date asDate(LocalDate localDate) {
        return Date.from(localDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
    }

    public static Date asDate(LocalDateTime localDateTime) {
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

    public static String toString(LocalDate date) {
        return date.format(DateTimeFormatter.ISO_DATE);
    }

    public static LocalDate todayDate() {
        Date date = new Date();
        return asDate(new SimpleDateFormat("yyyy-MM-dd").format(date));
    }

    public static LocalDate instantStringToLocalDate(String dateString) {
        Instant instant = Instant.from(DateTimeFormatter.ISO_INSTANT.parse(dateString));
        return LocalDate.ofInstant(instant, ZoneOffset.UTC);
    }

    public static LocalDateTime plusTime(Type type, long value) {
        LocalDateTime localDateTime = now();
        if (type == Type.HOURS) {
            localDateTime = localDateTime.plusHours(value);
        } else if (type == Type.DAYS) {
            localDateTime = localDateTime.plusDays(value);
        } else if (type == Type.MINUTES) {
            localDateTime = localDateTime.plusMinutes(2);
        } else if (type == Type.SECONDS) {
            localDateTime = localDateTime.plusSeconds(value);
        }
        return localDateTime;
    }

    public static long toMilliseconds(LocalDateTime localDateTime) {
        return localDateTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
    }

    public static LocalDateTime fromMilliseconds(long value) {
        return Instant.ofEpochMilli(value).atZone(ZoneId.systemDefault()).toLocalDateTime();
    }

    public static Instant fromLocalDateTime(LocalDateTime localDate) {
        return localDate.atZone(ZoneId.systemDefault()).toInstant();
    }

}
