package com.mei.animation.splash.widget.state

import android.animation.Animator
import android.animation.ValueAnimator
import android.graphics.Canvas
import android.graphics.Paint
import android.util.Log
import android.view.View
import android.view.animation.LinearInterpolator
import kotlin.math.sqrt

/**
 * @date 2021/1/17
 * @author mxb
 * @desc   * 3.水波纹扩散动画
 * 画一个空心圆----画一个圆，让它的画笔的粗细变成很大---不断地减小画笔的粗细。
 * 空心圆变化的范围：0~ 对角线/2
 * @desired
 */
class ExpandState(view: View) : SplashState(view) {

    private val TAG = "ExpandState"

    private var mRippleRadius = 0f
    private var mStrokeWidth = 0f

    private var mRipplePaint = Paint(Paint.ANTI_ALIAS_FLAG)

    init {
        mRipplePaint.style = Paint.Style.STROKE
        mRipplePaint.color = mBackgroundColor
    }


    override fun startAnimation(): Animator {
        // 取消之前的动画
        cancelAnimation()

        // 执行新的动画
        var width = centerX * 2
        var height = centerY * 2
        // 水波纹的直径
        var diameter = sqrt(width * width + height * height)
        var radius = diameter / 2f

        Log.i(TAG, "startAnimation: radius=$radius")

        var animator = ValueAnimator.ofFloat(mSmallCircleRadius, radius)
        animator.duration = mAnimationDuration
        animator.interpolator = LinearInterpolator()
        animator.addUpdateListener {
            mRippleRadius = it.animatedValue as Float
            // 水波纹圆的线宽
            // 得到画笔的宽度 = 对角线/2 - 空心圆的半径
            mStrokeWidth = radius - mRippleRadius
            Log.i(TAG, "startAnimation: strokeWidth=$mStrokeWidth")
            invalidate()
        }

        animator.start()

        return animator
    }

    // 绘制水波纹类型的背景
    override fun drawBackground(canvas: Canvas) {
        mRipplePaint.strokeWidth = mStrokeWidth
        //画圆的半径 = 空心圆的半径 + 画笔的宽度/2
        var radius = mRippleRadius + mStrokeWidth / 2
        canvas.drawCircle(centerX, centerY, radius, mRipplePaint)
    }

    override fun drawCircles(canvas: Canvas) {
        // 水波纹扩散状态的时候，不需要话小圆了
    }
}