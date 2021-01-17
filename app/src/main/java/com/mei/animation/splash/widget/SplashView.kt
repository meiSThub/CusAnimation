package com.mei.animation.splash.widget

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.os.Build
import android.util.AttributeSet
import android.view.View
import androidx.annotation.RequiresApi
import com.mei.animation.splash.widget.state.*
import kotlin.math.cos
import kotlin.math.sin

/**
 * @date 2021/1/17
 * @author mxb
 * @desc 酷炫的引导动画
 * @desired
 */
class SplashView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {

    private var mCenterX = 0f
    private var mCenterY = 0f

    private var mCircleNum = 6

    // 每个小圆的间隔角度
    private var mSwapAngle = Math.PI / 6

    // 当前旋转的角度
    private var mCurrentRotationAngle = 0.0

    // 每一个小圆的半径
    private var mSmallCircleRadius = 18f

    // 大圆(里面包含很多小圆的)的半径
    private var mRotationCircleRadius = 90f

    private var mSmallCirclePaint = Paint()

    // 背景色
    private var mBackgroundColor = Color.WHITE

    private var mCircleColors = intArrayOf(
        0xFFFF9600.toInt(),
        0xFF02D1AC.toInt(),
        0xFFFFD200.toInt(),
        0xFF00C6FF.toInt(),
        0xFF00E099.toInt(),
        0xFFFF3892.toInt()
    )

    // 当前的绘制状态
    private var mSplashState: SplashState = RotationState(this)

    init {
        mCircleNum = mCircleColors.size
        mSmallCirclePaint.isAntiAlias = true

        startSplash()

        postDelayed({
            changeToMergingState()
        }, 2 * 1000L)
    }


    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        mCenterX = w / 2f
        mCenterY = h / 2f
        mSplashState.centerX = w / 2f
        mSplashState.centerY = h / 2f
    }

    override fun onDraw(canvas: Canvas) {
        mSplashState.draw(canvas)
    }

    fun startSplash() {
        mSplashState.startAnimation()
    }

    fun changeToMergingState() {
        // 取消动画
        mSplashState.cancelAnimation()
        // 执行合并动画
        mSplashState = MergingState(this)
        mSplashState.centerX = mCenterX
        mSplashState.centerY = mCenterY
        var animator = mSplashState.startAnimation()
        animator.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator?) {
                super.onAnimationEnd(animation)
                mSplashState = ExpandState(this@SplashView)
                mSplashState.centerX = mCenterX
                mSplashState.centerY = mCenterY
                mSplashState.startAnimation()
            }
        })
    }

    /**
     * 绘制背景
     */
    private fun drawableBackground(canvas: Canvas) {
        canvas.drawColor(mBackgroundColor)
    }

    /**
     * 绘制五个小圆
     */
    private fun drawSmallCircle(canvas: Canvas) {

        // 小圆的数量
        var circleNum = mCircleColors.size
        // 每个小圆间隔的角度
        var gapAngle = 2 * Math.PI / circleNum
        // 每个小圆的圆心坐标
        var cx: Float
        var cy: Float
        for (i in 0 until mCircleNum) {
            /**
             * x = r*cos(a) +centerX
             * y=  r*sin(a) + centerY
             * 每个小圆i*间隔角度 + 旋转的角度 = 当前小圆的真是角度
             */
            var angle = mCurrentRotationAngle + gapAngle * i
            cx = (mRotationCircleRadius * cos(angle) + mCenterX).toFloat()
            cy = (mRotationCircleRadius * sin(angle) + mCenterY).toFloat()

            mSmallCirclePaint.color = mCircleColors[i]
            canvas.drawCircle(cx, cy, mSmallCircleRadius, mSmallCirclePaint)
        }
    }


}