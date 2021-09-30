package com.prolificinteractive.materialcalendarview.format

import com.prolificinteractive.materialcalendarview.CalendarDay
import android.text.SpannableStringBuilder

/**
 * Use an array to generate a month/year label.
 * @param monthLabels an array of 12 labels to use for months, starting with January
 */
class MonthArrayTitleFormatter(private val monthLabels: Array<CharSequence>) : TitleFormatter {
    /**
     * {@inheritDoc}
     */
    override fun format(day: CalendarDay): CharSequence =
        SpannableStringBuilder()
            .append(monthLabels[day.month - 1])
            .append(" ")
            .append(day.year.toString())

    /**
     * Format using an array of month labels
     *
     */
    init {
        require(monthLabels.size >= 12) { "Label array is too short" }
    }
}