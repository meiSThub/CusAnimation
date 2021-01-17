package com.mei.animation.splash.widget.state

import android.animation.Animator
import android.graphics.Canvas

/**
 * @date 2021/1/17
 * @author mxb
 * @desc 根据不同的状态，绘制View
 * @desired
 */
interface ISplashState {

    /**
     * 根据不同的状态，绘制
     */
    fun draw(canvas: Canvas)

    /**
     * 刷新UI
     */
    fun invalidate()

    /**
     * 不同的状态，执行不同的动画
     */
    fun startAnimation(): Animator

    /**
     * 取消动画
     */
    fun cancelAnimation()
}