package com.prolificinteractive.materialcalendarview

import android.graphics.drawable.Drawable
import java.util.LinkedList
import com.prolificinteractive.materialcalendarview.DayViewFacade
import java.util.Collections

/**
 * Abstraction layer to help in decorating Day views.
 */
class DayViewFacade internal constructor() {
    var isDecorated = false
        private set

    /** Drawable to draw behind everything. */
    var backgroundDrawable: Drawable? = null
        set(value) {
            field = value
            isDecorated = value != null
        }

    /** Drawable for selection. */
    //TODO: define states that can/should be used in StateListDrawables
    var selectionDrawable: Drawable? = null
        set(value) {
            field = value
            isDecorated = value != null
        }

    private val spans = LinkedList<Span>()

    /** Are days from this facade disabled. */
    var daysDisabled: Boolean = false
        private set

    /**
     * Add a span to the entire text of a day
     *
     * @param span text span instance
     */
    fun addSpan(span: Any) {
        spans.add(Span(span))
        isDecorated = true
    }

    /**
     *
     * Set days to be in a disabled state, or re-enabled.
     *
     * Note, passing true here will **not** override minimum and maximum dates, if set.
     * This will only re-enable disabled dates.
     *
     * @param daysDisabled true to disable days, false to re-enable days
     */
    fun setDaysDisabled(daysDisabled: Boolean) {
        this.daysDisabled = daysDisabled
        isDecorated = true
    }

    fun reset() {
        backgroundDrawable = null
        selectionDrawable = null
        spans.clear()
        isDecorated = false
        daysDisabled = false
    }

    /**
     * Apply things set this to other.
     * @param other facade to apply our data to.
     */
    fun applyTo(other: DayViewFacade) {
        selectionDrawable?.let { other.selectionDrawable = it }
        backgroundDrawable?.let { other.backgroundDrawable = it }
        other.spans.addAll(spans)
        other.isDecorated = other.isDecorated or isDecorated
        other.daysDisabled = daysDisabled
    }

    fun getSpans(): List<Span> =
        Collections.unmodifiableList(spans)

    class Span(val span: Any)
}