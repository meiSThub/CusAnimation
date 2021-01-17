package com.mei.animation.splash.widget.state

import android.animation.Animator
import android.animation.ValueAnimator
import android.view.View
import android.view.animation.LinearInterpolator

/**
 * @date 2021/1/17
 * @author mxb
 * @desc 1.旋转动画
 * 控制各个小圆的坐标---控制小圆的角度变化----属性动画ValueAnimator
 * @desired
 */
class RotationState(view: View) : SplashState(view) {

    /**
     * 执行旋转动画
     */
    override fun startAnimation(): Animator {
        // 取消之前的动画
        cancelAnimation()

        // 执行新的动画
        var animator = ValueAnimator.ofFloat(0f, (2 * Math.PI).toFloat())
        animator.duration = mAnimationDuration
        animator.addUpdateListener {
            mCurrentRotationAngle = it.animatedValue as Float
        }
        animator.interpolator = LinearInterpolator()
        animator.repeatCount = ValueAnimator.INFINITE
        animator.repeatMode = ValueAnimator.RESTART
        animator.addUpdateListener {
            invalidate()
        }
        animator.start()
        mAnimator = animator
        return animator
    }
}