package com.prolificinteractive.materialcalendarview

import java.time.DayOfWeek
import java.time.temporal.WeekFields
import java.time.temporal.ChronoUnit

class WeekPagerAdapter(materialCalendarView: MaterialCalendarView) : CalendarPagerAdapter<WeekView>(materialCalendarView) {
    override fun createView(position: Int) = WeekView(
        view = materialCalendarView,
        firstViewDay = getItem(position),
        firstDayOfWeek = materialCalendarView.firstDayOfWeek,
        showWeekDays = showWeekDays
    )

    override fun indexOf(view: WeekView): Int {
        val week = view.firstViewDay
        return rangeIndex.indexOf(week)
    }

    override fun isInstanceOfView(`object`: Any): Boolean = `object` is WeekView

    override fun createRangeIndex(min: CalendarDay, max: CalendarDay): DateRangeIndex =
        Weekly(min, max, materialCalendarView.firstDayOfWeek)

    class Weekly(
        min: CalendarDay,
        max: CalendarDay,
        /** First day of the week to base the weeks on. */
        private val firstDayOfWeek: DayOfWeek
    ) : DateRangeIndex {
        /** Minimum day of the first week to display. */
        private val min: CalendarDay = getFirstDayOfWeek(min)

        /** Number of weeks to show. */
        override val count: Int = indexOf(max) + 1

        override fun indexOf(day: CalendarDay): Int {
            val weekFields = WeekFields.of(firstDayOfWeek, 1)
            val temp = day.date.with(weekFields.dayOfWeek(), 1L)
            return ChronoUnit.WEEKS.between(min.date, temp).toInt()
        }

        override fun getItem(position: Int): CalendarDay =
            CalendarDay(date = min.date.plusWeeks(position.toLong()))

        /**
         * Getting the first day of a week for a specific date based on a specific week day as first day.
         */
        private fun getFirstDayOfWeek(day: CalendarDay) =
            CalendarDay(date = day.date.with(WeekFields.of(firstDayOfWeek, 1).dayOfWeek(), 1L))
    }
}
