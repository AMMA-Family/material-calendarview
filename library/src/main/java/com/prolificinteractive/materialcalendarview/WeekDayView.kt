package com.prolificinteractive.materialcalendarview

import android.annotation.SuppressLint
import android.content.Context
import java.time.DayOfWeek
import androidx.appcompat.widget.AppCompatTextView
import com.prolificinteractive.materialcalendarview.format.WeekDayFormatter
import android.view.Gravity

/**
 * Display a day of the week.
 */
@SuppressLint("ViewConstructor")
internal class WeekDayView(context: Context, dayOfWeek: DayOfWeek?) : AppCompatTextView(context) {
    init {
        gravity = Gravity.CENTER
        textAlignment = TEXT_ALIGNMENT_CENTER
    }

    private var formatter = WeekDayFormatter.DEFAULT
    private var dayOfWeek: DayOfWeek? = dayOfWeek
        set(value) {
            field = value
            text = formatter.format(value)
        }

    fun setWeekDayFormatter(weekDayFormatter: WeekDayFormatter?) {
        this.formatter = weekDayFormatter ?: WeekDayFormatter.DEFAULT
        text = formatter.format(dayOfWeek)
    }
}
