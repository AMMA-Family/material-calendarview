package com.prolificinteractive.materialcalendarview

import android.animation.Animator

internal open class AnimatorListener : Animator.AnimatorListener {
    override fun onAnimationStart(animator: Animator) = Unit
    override fun onAnimationEnd(animator: Animator) = Unit
    override fun onAnimationCancel(animator: Animator) = Unit
    override fun onAnimationRepeat(animator: Animator) = Unit
}