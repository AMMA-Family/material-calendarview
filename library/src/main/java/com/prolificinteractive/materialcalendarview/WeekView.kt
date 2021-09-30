package com.prolificinteractive.materialcalendarview

import android.annotation.SuppressLint
import java.time.DayOfWeek
import java.time.LocalDate

/**
 * Display a week of [DayView]s and seven [WeekDayView]s.
 */
@SuppressLint("ViewConstructor")
class WeekView(
    view: MaterialCalendarView,
    firstViewDay: CalendarDay,
    firstDayOfWeek: DayOfWeek,
    showWeekDays: Boolean
) : CalendarPagerView(view, firstViewDay, firstDayOfWeek, showWeekDays) {
    override fun buildDayViews(dayViews: Collection<DayView>, calendar: LocalDate) {
        var temp = calendar
        repeat(DEFAULT_DAYS_IN_WEEK) {
            addDayView(dayViews, temp)
            temp = temp.plusDays(1)
        }
    }

    override fun isDayEnabled(day: CalendarDay): Boolean = true

    override fun getRows(): Int =
        if (showWeekDays) DAY_NAMES_ROW + 1 else 1
}
