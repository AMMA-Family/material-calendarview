package com.prolificinteractive.materialcalendarview.format

import java.time.DayOfWeek

/**
 * Use an array to supply week day labels.
 * @param weekDayLabels an array of 7 labels, starting with Sunday.
 */
class ArrayWeekDayFormatter(private val weekDayLabels: Array<CharSequence>) : WeekDayFormatter {
    init {
        require(weekDayLabels.size == 7) { "Array must contain exactly 7 elements" }
    }

    /**
     * {@inheritDoc}
     */
    override fun format(dayOfWeek: DayOfWeek): CharSequence =
        weekDayLabels[dayOfWeek.value - 1]
}
