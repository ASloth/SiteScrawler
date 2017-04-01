package de.sitescrawler.utility;

import java.time.DayOfWeek;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DateUtils
{
    public static final String DATE_PATTERN = "EE dd.MM.yyyy";

    public static Date asDate(LocalDate localDate)
    {
        LocalDateTime atStartOfDay = localDate.atStartOfDay();
        ZoneId systemDefault = ZoneId.systemDefault();
        ZonedDateTime atZone = atStartOfDay.atZone(systemDefault);
        Instant instant = atZone.toInstant();
        Date from = Date.from(instant);
        return from;
    }

    public static Date asDate(LocalDateTime localDateTime)
    {
        return localDateTime == null ? null : Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

    public static LocalDate asLocalDate(Date date)
    {
        return date == null ? null : Instant.ofEpochMilli(date.getTime()).atZone(ZoneId.systemDefault()).toLocalDate();
    }

    public static LocalDateTime asLocalDateTime(Date date)
    {
        return date == null ? null : Instant.ofEpochMilli(date.getTime()).atZone(ZoneId.systemDefault()).toLocalDateTime();
    }

    public static long businessDaysBetween(LocalDate start, LocalDate stop)
    {
        return DateUtils.businessDaysBetween(start, stop, new ArrayList<LocalDate>());
    }

    public static long businessDaysBetween(LocalDate start, LocalDate stop, List<LocalDate> holidays)
    {
        if (start.isAfter(stop))
        {
            return -1;
        }
        long count = 0;
        while (start.isBefore(stop) || start.isEqual(stop))
        {
            DayOfWeek dayOfWeek = start.getDayOfWeek();
            if (!dayOfWeek.equals(DayOfWeek.SATURDAY) && !dayOfWeek.equals(DayOfWeek.SUNDAY) && !DateUtils.containsDate(holidays, start))
            {
                count++;
            }
            start = start.plusDays(1);
        }
        return count;

    }

    private static boolean containsDate(List<LocalDate> list, LocalDate date)
    {
        return list.stream().anyMatch(listDate -> listDate.isEqual(date));
    }
}