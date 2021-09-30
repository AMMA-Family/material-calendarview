package com.prolificinteractive.materialcalendarview.format

import com.prolificinteractive.materialcalendarview.CalendarDay
import java.time.format.DateTimeFormatter

/**
 * Format using a [java.text.DateFormat] instance.
 */
class DateFormatTitleFormatter @JvmOverloads constructor(
    private val dateFormat: DateTimeFormatter = DateTimeFormatter.ofPattern(TitleFormatter.DEFAULT_FORMAT)
) : TitleFormatter {
    /**
     * {@inheritDoc}
     */
    override fun format(day: CalendarDay): CharSequence =
        dateFormat.format(day.date)
}