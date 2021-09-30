package com.prolificinteractive.materialcalendarview

import com.prolificinteractive.materialcalendarview.format.DayFormatter.Companion.DEFAULT
import android.annotation.SuppressLint
import androidx.appcompat.widget.AppCompatCheckedTextView
import android.graphics.drawable.Drawable
import com.prolificinteractive.materialcalendarview.format.DayFormatter
import com.prolificinteractive.materialcalendarview.MaterialCalendarView.ShowOtherDates
import android.text.Spanned
import android.text.SpannableString
import android.graphics.Canvas
import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.OvalShape
import android.annotation.TargetApi
import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.Rect
import android.graphics.drawable.RippleDrawable
import android.graphics.drawable.StateListDrawable
import android.os.Build
import android.view.Gravity
import kotlin.math.abs

/**
 * Display one day of a [MaterialCalendarView]
 */
@SuppressLint("ViewConstructor")
class DayView(context: Context?, day: CalendarDay?) : AppCompatCheckedTextView(context!!) {
    var date: CalendarDay? = null
        private set
    private var selectionColor = Color.GRAY
    private val fadeTime: Int = resources.getInteger(android.R.integer.config_shortAnimTime)
    private var customBackground: Drawable? = null
    private var selectionDrawable: Drawable? = null
    private var mCircleDrawable: Drawable? = null
    private var formatter = DEFAULT
    private var contentDescriptionFormatter: DayFormatter? = formatter
    private var isInRange = true
    private var isInMonth = true
    private var isDecoratedDisabled = false

    @ShowOtherDates
    private var showOtherDates = MaterialCalendarView.SHOW_DEFAULTS
    fun setDay(date: CalendarDay?) {
        this.date = date
        text = label
    }

    /**
     * Set the new label formatter and reformat the current label. This preserves current spans.
     *
     * @param formatter new label formatter
     */
    fun setDayFormatter(formatter: DayFormatter?) {
        contentDescriptionFormatter = if (contentDescriptionFormatter === this.formatter) formatter else contentDescriptionFormatter
        this.formatter = formatter ?: DEFAULT
        val currentLabel = text
        var spans: Array<Any?>? = null
        if (currentLabel is Spanned) {
            spans = currentLabel.getSpans(0, currentLabel.length, Any::class.java)
        }
        val newLabel = SpannableString(label)
        if (spans != null) {
            for (span in spans) {
                newLabel.setSpan(span, 0, newLabel.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
            }
        }
        text = newLabel
    }

    /**
     * Set the new content description formatter and reformat the current content description.
     *
     * @param formatter new content description formatter
     */
    fun setDayFormatterContentDescription(formatter: DayFormatter?) {
        contentDescriptionFormatter = formatter ?: this.formatter
        contentDescription = contentDescriptionLabel
    }

    val label: String
        get() = formatter.format(date!!)
    val contentDescriptionLabel: String
        get() = if (contentDescriptionFormatter == null) formatter.format(date!!) else contentDescriptionFormatter!!.format(date!!)

    fun setSelectionColor(color: Int) {
        selectionColor = color
        regenerateBackground()
    }

    /**
     * @param drawable custom selection drawable
     */
    fun setSelectionDrawable(drawable: Drawable?) {
        selectionDrawable = drawable?.constantState?.newDrawable(resources)
        regenerateBackground()
    }

    /**
     * @param drawable background to draw behind everything else
     */
    fun setCustomBackground(drawable: Drawable?) {
        customBackground = drawable?.constantState?.newDrawable(resources)
        invalidate()
    }

    private fun setEnabled() {
        val enabled = isInMonth && isInRange && !isDecoratedDisabled
        super.setEnabled(isInRange && !isDecoratedDisabled)
        val showOtherMonths = MaterialCalendarView.showOtherMonths(showOtherDates)
        val showOutOfRange = MaterialCalendarView.showOutOfRange(showOtherDates) || showOtherMonths
        val showDecoratedDisabled = MaterialCalendarView.showDecoratedDisabled(showOtherDates)
        var shouldBeVisible = enabled
        if (!isInMonth && showOtherMonths) {
            shouldBeVisible = true
        }
        if (!isInRange && showOutOfRange) {
            shouldBeVisible = shouldBeVisible or isInMonth
        }
        if (isDecoratedDisabled && showDecoratedDisabled) {
            shouldBeVisible = shouldBeVisible or (isInMonth && isInRange)
        }
        if (!isInMonth && shouldBeVisible) {
            setTextColor(textColors.getColorForState(intArrayOf(-android.R.attr.state_enabled), Color.GRAY))
        }
        visibility = if (shouldBeVisible) VISIBLE else INVISIBLE
    }

    fun setupSelection(
        @ShowOtherDates showOtherDates: Int,
        inRange: Boolean,
        inMonth: Boolean
    ) {
        this.showOtherDates = showOtherDates
        isInMonth = inMonth
        isInRange = inRange
        setEnabled()
    }

    private val tempRect = Rect()
    private val circleDrawableRect = Rect()
    override fun onDraw(canvas: Canvas) {
        customBackground?.apply {
            bounds = tempRect
            state = drawableState
            draw(canvas)
        }
        mCircleDrawable?.bounds = circleDrawableRect
        super.onDraw(canvas)
    }

    private fun regenerateBackground() {
        background = selectionDrawable ?: generateBackground(selectionColor, fadeTime, circleDrawableRect).also { mCircleDrawable = it }
    }

    /**
     * @param facade apply the facade to us
     */
    fun applyFacade(facade: DayViewFacade) {
        isDecoratedDisabled = facade.daysDisabled
        setEnabled()
        setCustomBackground(facade.backgroundDrawable)
        setSelectionDrawable(facade.selectionDrawable)

        // Facade has spans
        val spans = facade.getSpans()
        text = if (spans.isNotEmpty()) {
            val label = label
            val formattedLabel = SpannableString(label)
            for (span in spans) {
                formattedLabel.setSpan(span.span, 0, label.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
            }
            formattedLabel
        } else {
            label
        }
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        calculateBounds(right - left, bottom - top)
        regenerateBackground()
    }

    private fun calculateBounds(width: Int, height: Int) {
        val radius = height.coerceAtMost(width)
        val offset = abs(height - width) / 2

        // Lollipop platform bug. Circle drawable offset needs to be half of normal offset
        val circleOffset = if (Build.VERSION.SDK_INT == Build.VERSION_CODES.LOLLIPOP) offset / 2 else offset
        if (width >= height) {
            tempRect[offset, 0, radius + offset] = height
            circleDrawableRect[circleOffset, 0, radius + circleOffset] = height
        } else {
            tempRect[0, offset, width] = radius + offset
            circleDrawableRect[0, circleOffset, width] = radius + circleOffset
        }
    }

    companion object {
        private fun generateBackground(color: Int, fadeTime: Int, bounds: Rect): Drawable =
            StateListDrawable().apply {
                setExitFadeDuration(fadeTime)
                addState(intArrayOf(android.R.attr.state_checked), generateCircleDrawable(color))
                addState(
                    intArrayOf(android.R.attr.state_pressed),
                    generateRippleDrawable(color, bounds)
                )
                addState(intArrayOf(), generateCircleDrawable(Color.TRANSPARENT))
            }

        private fun generateCircleDrawable(color: Int): Drawable =
            ShapeDrawable(OvalShape()).apply { paint.color = color  }

        private fun generateRippleDrawable(color: Int, bounds: Rect): Drawable {
            val list = ColorStateList.valueOf(color)
            val mask = generateCircleDrawable(Color.WHITE)
            val rippleDrawable = RippleDrawable(list, null, mask)
            // API 21
            if (Build.VERSION.SDK_INT == Build.VERSION_CODES.LOLLIPOP) {
                rippleDrawable.bounds = bounds
            }

            // API 22. Technically harmless to leave on for API 21 and 23, but not worth risking for 23+
            if (Build.VERSION.SDK_INT == Build.VERSION_CODES.LOLLIPOP_MR1) {
                val center = (bounds.left + bounds.right) / 2
                rippleDrawable.setHotspotBounds(center, bounds.top, center, bounds.bottom)
            }
            return rippleDrawable
        }
    }

    init {
        setSelectionColor(selectionColor)
        gravity = Gravity.CENTER
        textAlignment = TEXT_ALIGNMENT_CENTER
        setDay(day)
    }
}