package com.mei.animation.cuslayoutanimation.widget

import android.view.View

/**
 * @date 2021/1/17
 * @author mxb
 * @desc 处理动画的接口
 * @desired
 */
interface IHandleAnimation {

    /**
     * 执行动画
     * @param view 执行动画的View
     * @param fraction 进度 取值范围 0～1
     */
    fun startAnimation(view: View, fraction: Float)

    /**
     * 恢复动画
     * @param view 执行动画的View
     */
    fun resetAnimation(view: View)
}