package ua.teachme.util.notation;

import ua.teachme.model.Notation;
import ua.teachme.dto.NotationExceed;
import ua.teachme.util.time.TimeUtil;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;
import java.util.stream.Collectors;

//todo: clear unused code
public class NotationUtil {

    public static int hours = 8;

    public static List<Notation> notations = Arrays.asList(
            new Notation("notation1", "http://url1.com", "desc1", 1, LocalDateTime.now()),
            new Notation("notation2", "http://url2.com", "desc2", 2, LocalDateTime.now()),
            new Notation("notation3", "http://url3.com", "desc1", 3, LocalDateTime.now()),
            new Notation("notation4", "http://url4.com", "desc1", 4, LocalDateTime.now()),
            new Notation("notation5", "http://url5.com", "desc1", 5, LocalDateTime.of(2016, Month.MARCH, 13, 11, 35)),
            new Notation("notation6", "http://url6.com", "desc1", 6, LocalDateTime.of(2016, Month.MARCH, 13, 21, 55))
    );

    public static Notation newNotation = new Notation("name", "URL", "description", 1, LocalDateTime.now());
    public static Notation equalNotation = new Notation("name", "URL", "description", 1, LocalDateTime.now());

    public static void main(String[] args) {
        getFilteredNotationsExceedWithStreams(notations, LocalTime.of(11, 0), LocalTime.of(23, 0), hours).forEach(System.out::println);
        System.out.println("///////////////");
        getFilteredNotationsExceedWithLoops(notations, LocalTime.of(11, 0), LocalTime.of(23, 0), hours).forEach(System.out::println);
        System.out.println("///////////////");
        getFilteredWithExceed(notations, hours).forEach(System.out::println);
        System.out.println("///////////////");
    }

    //filter with full day period
    public static List<NotationExceed> getFilteredWithExceed(List<Notation> notations, int hoursPerDay){
        return getFilteredNotationsExceedWithStreams(notations, LocalTime.MIN, LocalTime.MAX, hoursPerDay);
    }

    //filter with streams
    public static List<NotationExceed> getFilteredNotationsExceedWithStreams(List<Notation> notations, LocalTime startTime, LocalTime endTime, int hoursPerDay){
        Map<LocalDate, Integer> hoursSumByDate = notations.stream()
                .collect(
                        Collectors.groupingBy(
                                notation -> notation.getCreatedDateAndTime().toLocalDate(),
                                Collectors.summingInt(Notation::getHours)
                        )
                );

        return notations.stream()
                .filter(notation -> TimeUtil.isBetween(notation.getTime(), startTime, endTime))
                .map(notation -> new NotationExceed(notation, hoursSumByDate.get(notation.getDate()) > hoursPerDay))
                .collect(Collectors.toList());
    }

    //filter with loops
    public static List<NotationExceed> getFilteredNotationsExceedWithLoops(List<Notation> notations, LocalTime startTime, LocalTime endTime, int hoursPerDay){
        Map<LocalDate, Integer> hoursSumByDate = new HashMap<>();
        notations.forEach(notation -> hoursSumByDate.merge(notation.getDate(), notation.getHours(), Integer::sum));

        List<NotationExceed> notationExceedList = new ArrayList<>();
        notations.forEach(notation -> {
            if (TimeUtil.isBetween(notation.getTime(), startTime, endTime)){
                notationExceedList.add( new NotationExceed(notation, hoursSumByDate.get(notation.getDate()) > hoursPerDay ));
            }
        });
        return notationExceedList;
    }
}