package com.prolificinteractive.materialcalendarview

import android.annotation.SuppressLint
import java.time.DayOfWeek
import java.time.LocalDate

/**
 * Display a month of [DayView]s and
 * seven [WeekDayView]s.
 */
@SuppressLint("ViewConstructor")
internal class MonthView(
    view: MaterialCalendarView,
    month: CalendarDay,
    firstDayOfWeek: DayOfWeek,
    showWeekDays: Boolean
) : CalendarPagerView(view, month, firstDayOfWeek, showWeekDays) {
    override fun buildDayViews(dayViews: Collection<DayView>, calendar: LocalDate) {
        var temp = calendar
        repeat(DEFAULT_MAX_WEEKS * DEFAULT_DAYS_IN_WEEK) {
            addDayView(dayViews, temp)
            temp = temp.plusDays(1)
        }
    }

    val month: CalendarDay
        get() = firstViewDay

    override fun isDayEnabled(day: CalendarDay): Boolean {
        return day.month == firstViewDay.month
    }

    override fun getRows(): Int =
        if (showWeekDays) DEFAULT_MAX_WEEKS + DAY_NAMES_ROW else DEFAULT_MAX_WEEKS
}