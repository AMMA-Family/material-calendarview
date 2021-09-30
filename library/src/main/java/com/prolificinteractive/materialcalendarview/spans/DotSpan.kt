package com.prolificinteractive.materialcalendarview.spans

import android.text.style.LineBackgroundSpan
import com.prolificinteractive.materialcalendarview.spans.DotSpan
import android.graphics.Canvas
import android.graphics.Paint

/**
 * Span to draw a dot centered under a section of text.
 * @param color color of the dot.
 * @param radius radius for the dot.
 */
class DotSpan @JvmOverloads constructor(
    private val radius: Float = DEFAULT_RADIUS,
    private val color: Int = 0
): LineBackgroundSpan {
    override fun drawBackground(
        canvas: Canvas, paint: Paint,
        left: Int, right: Int, top: Int, baseline: Int, bottom: Int,
        charSequence: CharSequence,
        start: Int, end: Int, lineNum: Int
    ) {
        val oldColor = paint.color
        if (color != 0) {
            paint.color = color
        }
        canvas.drawCircle(((left + right) / 2).toFloat(), bottom + radius, radius, paint)
        paint.color = oldColor
    }

    companion object {
        /**
         * Default radius used.
         */
        const val DEFAULT_RADIUS = 3f
    }
}