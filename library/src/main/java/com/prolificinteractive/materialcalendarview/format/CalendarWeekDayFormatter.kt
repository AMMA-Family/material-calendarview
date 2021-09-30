package com.prolificinteractive.materialcalendarview.format

import java.time.DayOfWeek
import java.time.format.TextStyle
import java.util.Locale

/**
 * Format the day of the week with using [TextStyle.SHORT] by default.
 *
 * @see java.time.DayOfWeek.getDisplayName
 */
class CalendarWeekDayFormatter : WeekDayFormatter {
    /**
     * {@inheritDoc}
     */
    override fun format(dayOfWeek: DayOfWeek): CharSequence =
        dayOfWeek.getDisplayName(TextStyle.SHORT, Locale.getDefault())
}