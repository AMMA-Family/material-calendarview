package com.prolificinteractive.materialcalendarview

import android.content.Context
import androidx.viewpager.widget.ViewPager
import android.util.AttributeSet
import android.view.MotionEvent

/**
 * Custom ViewPager that allows swiping to be disabled.
 */
internal class CalendarPager @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : ViewPager(context, attrs) {
    /**
     * Enable disable viewpager scroll.
     * false to disable paging, true for paging (default)
     */
    var isPagingEnabled = true

    override fun onInterceptTouchEvent(ev: MotionEvent): Boolean {
        return isPagingEnabled && super.onInterceptTouchEvent(ev)
    }

    override fun onTouchEvent(ev: MotionEvent): Boolean {
        return isPagingEnabled && super.onTouchEvent(ev)
    }

    /**
     * Disables scrolling vertically when paging disabled, fixes scrolling for nested [androidx.viewpager.widget.ViewPager].
     */
    override fun canScrollVertically(direction: Int): Boolean =
        isPagingEnabled && super.canScrollVertically(direction)

    /**
     * Disables scrolling horizontally when paging disabled, fixes scrolling for nested [androidx.viewpager.widget.ViewPager].
     */
    override fun canScrollHorizontally(direction: Int): Boolean =
        isPagingEnabled && super.canScrollHorizontally(direction)
}