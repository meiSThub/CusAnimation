package com.mei.animation.splash.widget.state

import android.animation.Animator
import android.animation.ValueAnimator
import android.view.View
import android.view.animation.OvershootInterpolator

/**
 * @date 2021/1/17
 * @author mxb
 * @desc    2.聚合动画
 * 要素：大圆的半径不断地变大--变小----》小圆的坐标
 * @desired
 */
class MergingState(view: View) : SplashState(view) {

    /**
     * 执行聚合动画
     */
    override fun startAnimation(): Animator {
        // 取消之前的动画
        cancelAnimation()

        // 执行新的动画
        val translationAnimator = ValueAnimator.ofFloat(0f, mRotationCircleRadius)
        translationAnimator.duration = mAnimationDuration
        translationAnimator.interpolator = OvershootInterpolator(10f)
        translationAnimator.addUpdateListener {
            mRotationCircleRadius = it.animatedValue as Float
            invalidate()
        }
        translationAnimator.reverse()

        return translationAnimator
    }

}