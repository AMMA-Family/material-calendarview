package com.prolificinteractive.materialcalendarview.format

import com.prolificinteractive.materialcalendarview.CalendarDay

/**
 * Supply labels for a given day. Default implementation is to format using a [java.text.SimpleDateFormat].
 */
interface DayFormatter {
    /**
     * Format a given day into a string
     *
     * @param day the day
     * @return a label for the day
     */
    fun format(day: CalendarDay): String

    companion object {
        /**
         * Default format for displaying the day.
         */
        const val DEFAULT_FORMAT = "d"

        /**
         * Default implementation used by [com.prolificinteractive.materialcalendarview.MaterialCalendarView].
         */
        val DEFAULT: DayFormatter = DateFormatDayFormatter()
    }
}