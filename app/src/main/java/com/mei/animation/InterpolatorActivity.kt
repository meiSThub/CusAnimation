package com.mei.animation

import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.View
import android.view.animation.*
import androidx.appcompat.app.AppCompatActivity

/**
 * @date 2021/1/13
 * @author mxb
 * @desc 插值器
 * @desired
 */
class InterpolatorActivity : AppCompatActivity() {

    private var tvAnimationView: View? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_interpolator)
        tvAnimationView = findViewById(R.id.tvAnimation)
    }

    fun accelerateDecelerateInterpolator(view: View) {
        startAnimation(view, AccelerateDecelerateInterpolator())
    }

    fun accelerateInterpolator(view: View) {
        startAnimation(view, AccelerateInterpolator())
    }

    fun anticipateInterpolator(view: View) {
        startAnimation(view, AnticipateInterpolator(4f))
    }

    fun anticipateOvershootInterpolator(view: View) {
        startAnimation(view, AnticipateOvershootInterpolator())
    }

    fun bounceInterpolator(view: View) {
        startAnimation(view, BounceInterpolator())
    }

    fun cycleInterpolator(view: View) {
        startAnimation(view, CycleInterpolator(1f))
    }

    fun decelerateInterpolator(view: View) {
        startAnimation(view, DecelerateInterpolator())
    }

    fun overshootInterpolator(view: View) {
        startAnimation(view, OvershootInterpolator())
    }

    private fun startAnimation(view: View, interpolator: Interpolator) {
        var animator = ObjectAnimator.ofFloat(tvAnimationView ?: view, "translationY", 0f, 400f)
        animator.interpolator = interpolator
        animator.duration = 1000
        animator.start()
    }


}