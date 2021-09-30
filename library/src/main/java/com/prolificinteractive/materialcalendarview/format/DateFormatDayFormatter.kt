package com.prolificinteractive.materialcalendarview.format

import java.util.Locale
import com.prolificinteractive.materialcalendarview.CalendarDay
import java.time.format.DateTimeFormatter

/**
 * Format using a [java.text.DateFormat] instance.
 */
class DateFormatDayFormatter @JvmOverloads constructor(
    private val dateFormat: DateTimeFormatter = DateTimeFormatter.ofPattern(DayFormatter.DEFAULT_FORMAT, Locale.getDefault())
) : DayFormatter {
    /**
     * {@inheritDoc}
     */
    override fun format(day: CalendarDay): String =
        dateFormat.format(day.date)
}