package ua.teachme.util.time;

import org.apache.commons.lang3.StringUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class TimeUtil {

    public static final LocalDate TODAY = LocalDate.now();
    public static final LocalTime MIN_TIME = LocalTime.MIN;
    public static final LocalTime MAX_TIME = LocalTime.MAX;

    private static final DateTimeFormatter DATE_TIME_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public static <T extends Comparable<? super T>> boolean isBetween(T value, T start, T end) {
        return value.compareTo(start) >= 0 && value.compareTo(end) <= 0;
    }

    public static LocalDate toLocalDate(String str){
        return StringUtils.isEmpty(str) ? null : LocalDate.parse(str);
    }

    public static LocalTime toLocalTime(String str){
        return StringUtils.isEmpty(str) ? null : LocalTime.parse(str);
    }

    public static String toString(LocalDateTime localDateTime){
        return localDateTime == null ? "" : localDateTime.format(DATE_TIME_FORMAT);
    }
}