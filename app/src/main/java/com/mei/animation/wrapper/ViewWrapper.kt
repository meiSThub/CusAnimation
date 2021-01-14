package com.mei.animation.wrapper

import android.view.View

/**
 * @date 2021/1/12
 * @author mxb
 * @desc View包装类
 * @desired
 */
class ViewWrapper(private var mTarget: View) {

    /**
     * 获取宽度
     */
    fun getWidth(): Int {
        return mTarget.layoutParams.width
    }

    /**
     * 获取高度
     */
    fun getHeight(): Int {
        return mTarget.layoutParams.height
    }

    /**
     * 设置宽度
     */
    fun setWidth(width: Int) {
        mTarget.layoutParams.width = width
        mTarget.requestLayout()
    }

    /**
     * 设置高度
     */
    fun setHeight(height: Int) {
        mTarget.layoutParams.height = height
        mTarget.requestLayout()
    }

}