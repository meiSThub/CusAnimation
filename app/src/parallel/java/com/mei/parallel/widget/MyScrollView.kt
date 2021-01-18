package com.mei.parallel.widget

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.widget.HorizontalScrollView

/**
 * @date 2021/1/18
 * @author mxb
 * @desc 自定义一个ScrollView，为了拦截事件，但不处理事件
 * @desired
 */
class MyScrollView(context: Context?, attrs: AttributeSet?) : HorizontalScrollView(context, attrs) {

    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {
        return true
    }

    override fun onTouchEvent(ev: MotionEvent?): Boolean {
        return false
    }
}