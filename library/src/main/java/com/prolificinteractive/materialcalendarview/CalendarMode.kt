package com.prolificinteractive.materialcalendarview

/**
 * Different Calendar types available for the pager, which will give the amount of weeks visible.
 * @param visibleWeeksCount Number of visible weeks per calendar mode.
 */
enum class CalendarMode(val visibleWeeksCount: Int) {
    /**
     * Month Mode to display an entire month per page.
     */
    MONTHS(6),

    /**
     * Week mode that shows the calendar week by week.
     */
    WEEKS(1);
}