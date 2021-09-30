package com.prolificinteractive.materialcalendarview

import android.os.Parcelable
import android.os.Parcel
import java.time.LocalDate

/**
 * An imputable representation of a day on a calendar, based on [LocalDate].
 */
class CalendarDay : Parcelable {
    /**
     * Everything is based on this variable for [CalendarDay].
     */
    val date: LocalDate

    /**
     * @param year New instance's year.
     * @param month New instance's month as defined by [java.util.Calendar].
     * @param day New instance's day of month.
     */
    constructor(year: Int, month: Int, day: Int) {
        date = LocalDate.of(year, month, day)
    }

    /**
     * @param date [LocalDate] instance.
     */
    constructor(date: LocalDate) {
        this.date = date
    }

    /** The year for this day. */
    val year: Int get() = date.year

    /** The month, represented by values from [LocalDate]. */
    val month: Int get() = date.monthValue

    /** The day of the month for this day. */
    val day: Int get() = date.dayOfMonth

    /**
     * Determine if this day is within a specified range
     *
     * @param minDate the earliest day, may be null
     * @param maxDate the latest day, may be null
     * @return true if the between (inclusive) the min and max dates.
     */
    fun isInRange(minDate: CalendarDay?, maxDate: CalendarDay?): Boolean {
        return !(minDate != null && minDate.isAfter(this)) &&
                !(maxDate != null && maxDate.isBefore(this))
    }

    /**
     * Determine if this day is before the given instance
     *
     * @param other the other day to test
     * @return true if this is before other, false if equal or after
     */
    fun isBefore(other: CalendarDay): Boolean {
        return date.isBefore(other.date)
    }

    /**
     * Determine if this day is after the given instance
     *
     * @param other the other day to test
     * @return true if this is after other, false if equal or before
     */
    fun isAfter(other: CalendarDay): Boolean =
        date.isAfter(other.date)

    override fun equals(other: Any?): Boolean =
        other is CalendarDay && date == other.date

    override fun hashCode(): Int =
        hashCode(date.year, date.monthValue, date.dayOfMonth)

    override fun toString(): String =
        ("CalendarDay{" + date.year + "-" + date.monthValue + "-"
                + date.dayOfMonth + "}")

    /* Parcelable Stuff */
    constructor(`in`: Parcel) : this(`in`.readInt(), `in`.readInt(), `in`.readInt()) {}

    override fun describeContents(): Int = 0

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeInt(date.year)
        dest.writeInt(date.monthValue)
        dest.writeInt(date.dayOfMonth)
    }

    companion object {
        /**
         * Get a new instance set to today
         *
         * @return CalendarDay set to today's date
         */
        @JvmStatic
        fun today(): CalendarDay? =
            LocalDate.now()?.let(::CalendarDay)

        private fun hashCode(year: Int, month: Int, day: Int): Int {
            //Should produce hashes like "20150401"
            return year * 10000 + month * 100 + day
        }

        @JvmField
        val CREATOR: Parcelable.Creator<CalendarDay> = object : Parcelable.Creator<CalendarDay> {
            override fun createFromParcel(`in`: Parcel) =CalendarDay(`in`)
            override fun newArray(size: Int): Array<CalendarDay?> =arrayOfNulls(size)
        }
    }
}