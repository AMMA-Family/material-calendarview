package com.prolificinteractive.materialcalendarview

import java.time.Period

/**
 * Pager adapter backing the calendar view
 */
internal class MonthPagerAdapter(materialCalendarView: MaterialCalendarView) : CalendarPagerAdapter<MonthView>(materialCalendarView) {
    override fun createView(position: Int) = MonthView(
        view = materialCalendarView,
        month = getItem(position), firstDayOfWeek = materialCalendarView.firstDayOfWeek,
        showWeekDays = showWeekDays
    )

    override fun indexOf(view: MonthView): Int {
        val month = view.month
        return rangeIndex.indexOf(month)
    }

    override fun isInstanceOfView(`object`: Any): Boolean = `object` is MonthView

    override fun createRangeIndex(min: CalendarDay, max: CalendarDay): DateRangeIndex =
        Monthly(min, max)

    class Monthly(min: CalendarDay, max: CalendarDay) : DateRangeIndex {
        /** Number of months to display. */
        override val count: Int = indexOf(max) + 1

        /** Minimum date with the first month to display. */
        private val min = CalendarDay(min.year, min.month, day = 1)

        override fun indexOf(day: CalendarDay): Int =
            Period
                .between(min.date.withDayOfMonth(1), day.date.withDayOfMonth(1))
                .toTotalMonths()
                .toInt()

        override fun getItem(position: Int): CalendarDay =
            CalendarDay(date = min.date.plusMonths(position.toLong()))
    }
}