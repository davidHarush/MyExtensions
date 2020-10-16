package com.david.haru.myextensions

import android.os.Build
import android.view.View
import android.view.ViewAnimationUtils
import android.webkit.WebSettings
import android.webkit.WebView
import android.widget.TextView
import androidx.core.animation.doOnEnd
import java.lang.Math.max


fun TextView.getTrimText(): String = this.text.toString().trim()


fun WebView.setDarkMode() {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            settings.forceDark = WebSettings.FORCE_DARK_ON
        }
}

fun View.visible() {
    visibility = View.VISIBLE
}

fun View.gone() {
    visibility = View.GONE
}

fun View.fadeInAnimate(duration: Long = 1500) {
    clearAnimation()
    alpha = 0f
    visible()
    animate().setDuration(duration).alphaBy(1f).start()
}
fun View.fadeOutAnimate(duration: Long = 1500) {
    clearAnimation()
    alpha = 1f
    animate().setDuration(duration).alpha(0f).withEndAction { gone() }.start()
}

fun View.revealAnim(startX :Int = width/2, startY: Int =height/2, duration:Long = 500 , startDelay:Long =0L) {
    val finalRadius = kotlin.math.max(width, height) *2
    val animator =
        ViewAnimationUtils.createCircularReveal(this, startX, startY, 0f, finalRadius.toFloat())
    visibility = View.VISIBLE
    animator.duration = duration
    animator.startDelay = startDelay
    clearAnimation()
    animator.start()
}

fun View.unRevealAnim(startX :Int = width/2, startY: Int =height/2, duration:Long = 500 , startDelay:Long =0L) {
    val initialRadius = kotlin.math.max(width, height) * 2
    val animator =
        ViewAnimationUtils.createCircularReveal(this, startX, startY, initialRadius.toFloat(), 0f)
    animator.duration = duration
    animator.startDelay = startDelay
    clearAnimation()
    animator.doOnEnd {gone()}
    animator.start()
}
