package com.prolificinteractive.materialcalendarview

import android.util.DisplayMetrics
import android.util.TypedValue
import androidx.core.text.TextUtilsCompat
import java.util.Locale
import androidx.core.view.ViewCompat
import kotlin.math.roundToInt

fun isRTL(locale: Locale = Locale.getDefault()): Boolean =
    TextUtilsCompat.getLayoutDirectionFromLocale(locale) == ViewCompat.LAYOUT_DIRECTION_RTL

fun Int.dpToPx(displayMetrics: DisplayMetrics): Int =
    TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, toFloat(), displayMetrics).roundToInt()