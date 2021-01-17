package com.mei.animation.splash.widget.state

import android.animation.Animator
import android.animation.ValueAnimator
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.Log
import android.view.View
import android.view.animation.LinearInterpolator
import java.lang.ref.WeakReference
import kotlin.math.cos
import kotlin.math.sin

/**
 * @date 2021/1/17
 * @author mxb
 * @desc 当前页面的绘制状态
 * @desired
 */
abstract class SplashState(view: View) : ISplashState {

    private val TAG = "SplashState"

    var centerX = 0f
    var centerY = 0f

    protected val mAnimationDuration = 1000L

    // view包装
    private var mViewReference: WeakReference<View>? = null

    // 当前旋转的角度
    protected var mCurrentRotationAngle = 0f

    // 每一个小圆的半径
    protected var mSmallCircleRadius = 18f

    // 大圆(里面包含很多小圆的)的半径
    protected var mRotationCircleRadius = 90f

    private var mSmallCirclePaint = Paint()

    // 背景色
    protected var mBackgroundColor = Color.WHITE

    protected var mAnimator: Animator? = null

    // 小圆颜色数组
    private var mCircleColors = intArrayOf(
        0xFFFF9600.toInt(),
        0xFF02D1AC.toInt(),
        0xFFFFD200.toInt(),
        0xFF00C6FF.toInt(),
        0xFF00E099.toInt(),
        0xFFFF3892.toInt()
    )


    init {
        mSmallCirclePaint.isAntiAlias = true
        Log.i(TAG, ": view=$view")
        mViewReference = WeakReference(view)
    }

    /**
     * 根据不同的状态，绘制
     */
    override fun draw(canvas: Canvas) {
        drawBackground(canvas)
        drawCircles(canvas)
    }

    /**
     * 绘制背景
     */
    open fun drawBackground(canvas: Canvas) {
        canvas.drawColor(mBackgroundColor)
    }

    /**
     * 绘制6个小圆
     */
    open fun drawCircles(canvas: Canvas) {
        // 小圆的数量
        var circleNum = mCircleColors.size
        // 每个小圆间隔的角度
        var gapAngle = 2 * Math.PI / circleNum
        // 每个小圆的圆心坐标
        var cx: Float
        var cy: Float
        for (i in 0 until circleNum) {
            /**
             * x = r*cos(a) +centerX
             * y=  r*sin(a) + centerY
             * 每个小圆i*间隔角度 + 旋转的角度 = 当前小圆的真是角度
             */
            var angle = mCurrentRotationAngle + gapAngle * i
            cx = (mRotationCircleRadius * cos(angle) + centerX).toFloat()
            cy = (mRotationCircleRadius * sin(angle) + centerY).toFloat()

            mSmallCirclePaint.color = mCircleColors[i]
            canvas.drawCircle(cx, cy, mSmallCircleRadius, mSmallCirclePaint)
        }
    }

    override fun invalidate() {
        var view = mViewReference?.get()
        view?.invalidate()
    }

    override fun cancelAnimation() {
        mAnimator?.cancel()
    }
}