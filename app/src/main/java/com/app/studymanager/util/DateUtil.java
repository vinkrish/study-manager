package com.app.studymanager.util;

import com.app.studymanager.models.ProficiencyValue;
import com.app.studymanager.models.WeeklyHours;

import org.joda.time.LocalDate;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Vinay on 27-11-2016.
 */

public class DateUtil {

    public static String getDisplayTargetDate(String dateString) {
        SimpleDateFormat displayFormat = new SimpleDateFormat("dd MMM yyyy", Locale.ENGLISH);
        SimpleDateFormat defaultFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        Date date = new Date();
        try {
            date = defaultFormat.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return displayFormat.format(date);
    }

    /*public LocalDate calculateTargetDate(WeeklyHours weeklyHours, ProficiencyValue proficiencyValue,
                                         String proficiency, int pagesUnread) {
        int pagesPerHour = 0;
        if (proficiency.equals("EASY")) {
            pagesPerHour = proficiencyValue.getBeginner();
        } else if (proficiency.equals("NORMAL")) {
            pagesPerHour = proficiencyValue.getNormal();
        } else if (proficiency.equals("EXPERT")) {
            pagesPerHour = proficiencyValue.getExpert();
        }
        int totalPagesPerWeek = getTotalPagesPerWeek(weeklyHours, pagesPerHour)

        double weeksToComplete = pagesUnread / totalPagesPerWeek;
        long daysToComplete = (int) Math.ceil(weeksToComplete) * 7;
        LocalDate newEndDate = userCoursesEntity.getStartDate().plusDays(daysToComplete);
        LocalDate absoluteEndDate = getAbsoluteEndDate(newEndDate, weeklyHours);
        return absoluteEndDate;
    }

    public static int getTotalPagesPerWeek(WeeklyHours weeklyHours, int pagesPerHour) {

        int totalPagesPerWeek = (weeklyHours.getMonday() * pagesPerHour)
                + (weeklyHours.getTuesday() * pagesPerHour)
                + (weeklyHours.getWednesday() * pagesPerHour)
                + (weeklyHours.getThursday() * pagesPerHour)
                + (weeklyHours.getFriday() * pagesPerHour)
                + (weeklyHours.getSaturday() * pagesPerHour)
                + (weeklyHours.getSunday() * pagesPerHour);
        return totalPagesPerWeek;
    }

    public static LocalDate getAbsoluteEndDate(LocalDate endDate, WeeklyHours weeklyHours) {
        DayOfWeek actualDayOfWeek = getNonZeroDay(weeklyHours);
        return endDate.with(TemporalAdjusters.next(actualDayOfWeek));
    }

    public static DayOfWeek getNonZeroDay(WeeklyHours weeklyHours) {
        if (weekDayHours.monday > 0) {
            return DayOfWeek.MONDAY;
        } else if (weekDayHours.tuesday > 0) {
            return DayOfWeek.TUESDAY;
        } else if (weekDayHours.wednesday > 0) {
            return DayOfWeek.WEDNESDAY;
        } else if (weekDayHours.thursday > 0) {
            return DayOfWeek.THURSDAY;
        } else if (weekDayHours.friday > 0) {
            return DayOfWeek.FRIDAY;
        } else if (weekDayHours.saturday > 0) {
            return DayOfWeek.SATURDAY;
        } else {
            return DayOfWeek.SUNDAY;
        }
    }*/

}