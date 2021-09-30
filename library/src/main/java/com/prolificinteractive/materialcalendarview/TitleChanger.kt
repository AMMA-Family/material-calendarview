package com.prolificinteractive.materialcalendarview

import com.prolificinteractive.materialcalendarview.format.TitleFormatter
import android.animation.Animator
import android.view.ViewPropertyAnimator
import android.view.animation.DecelerateInterpolator
import android.view.animation.Interpolator
import android.widget.TextView

internal class TitleChanger(private val title: TextView) {
    var titleFormatter: TitleFormatter = TitleFormatter.DEFAULT
    var orientation = MaterialCalendarView.VERTICAL
    var previousMonth: CalendarDay? = null

    private val animDelay: Int = DEFAULT_ANIMATION_DELAY
    private val animDuration: Int = title.resources.getInteger(android.R.integer.config_shortAnimTime) / 2
    private val translate: Int = DEFAULT_Y_TRANSLATION_DP.dpToPx(title.resources.displayMetrics)
    private val interpolator: Interpolator = DecelerateInterpolator(2f)
    private var lastAnimTime: Long = 0

    fun change(currentMonth: CalendarDay) {
        val currentTime = System.currentTimeMillis()
        if (title.text.isEmpty() || currentTime - lastAnimTime < animDelay) {
            doChange(currentTime, currentMonth, previousMonth, false)
        }
        if (currentMonth == previousMonth ||
            (currentMonth.month == previousMonth?.month && currentMonth.year == previousMonth?.year)
        ) {
            return
        }
        doChange(currentTime, currentMonth, previousMonth, true)
    }

    private fun doChange(now: Long, currentMonth: CalendarDay, previousMonth: CalendarDay?, animate: Boolean) {
        title.animate().cancel()
        title.doTranslation( 0)
        title.alpha = 1f
        lastAnimTime = now
        val newTitle = titleFormatter.format(currentMonth)
        if (previousMonth == null || !animate) {
            title.text = newTitle
        } else {
            val translation = translate * if (previousMonth.isBefore(currentMonth)) 1 else -1
            title
                .animate()
                .also { it.doTranslation(translation * -1) }
                .alpha(0f)
                .setDuration(animDuration.toLong())
                .setInterpolator(interpolator)
                .setListener(object : AnimatorListener() {
                    override fun onAnimationCancel(animator: Animator) {
                        title.doTranslation( 0)
                        title.alpha = 1f
                    }

                    override fun onAnimationEnd(animator: Animator) {
                        title.text = newTitle
                        title.doTranslation(translation)
                        title
                            .animate()
                            .also { it.doTranslation(0) }
                            .alpha(1f)
                            .setDuration(animDuration.toLong())
                            .setInterpolator(interpolator)
                            .setListener(AnimatorListener())
                            .start()
                    }
                }).start()
        }
        this.previousMonth = currentMonth
    }

    private fun ViewPropertyAnimator.doTranslation(translate: Int) {
        if (orientation == MaterialCalendarView.HORIZONTAL) {
            translationX(translate.toFloat())
        } else {
            translationY(translate.toFloat())
        }
    }

    private fun TextView.doTranslation(translate: Int) {
        if (orientation == MaterialCalendarView.HORIZONTAL) {
            translationX = translate.toFloat()
        } else {
            translationY = translate.toFloat()
        }
    }

    private companion object {
        const val DEFAULT_ANIMATION_DELAY = 400
        const val DEFAULT_Y_TRANSLATION_DP = 20
    }
}