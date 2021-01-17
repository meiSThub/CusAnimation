package com.mei.animation.cuslayoutanimation.widget

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.ViewGroup
import android.widget.ScrollView
import androidx.core.view.get
import kotlin.math.max
import kotlin.math.min

/**
 * @date 2021/1/17
 * @author mxb
 * @desc 自定义ScrollView，方便在内部监听滑动事件，避免使用者去监听滑动事件并执行动画
 *
 * 即自定义ScrollView的目的就是，实现自动执行动画
 * @desired
 */
class MyScrollView(context: Context?, attrs: AttributeSet?) : ScrollView(context, attrs) {

    private val TAG = "MyScrollView"

    private var mContentChild: ViewGroup? = null

    override fun onFinishInflate() {
        super.onFinishInflate()
        var view = getChildAt(0)
        if (view is ViewGroup) {
            mContentChild = view
        }
    }

    override fun onScrollChanged(l: Int, t: Int, oldl: Int, oldt: Int) {
        super.onScrollChanged(l, t, oldl, oldt)

        if (mContentChild == null || mContentChild!!.childCount <= 0) {
            return
        }

        var childCount = mContentChild!!.childCount

        //监听滑动的程度---childView从下面冒出来多少距离/childView.getHeight();----0~1：动画执行的百分比ratio
        //动画执行的百分比ratio控制动画执行
        for (i in 0 until childCount) {
            var child = mContentChild!!.getChildAt(i)

            var layoutParams = child!!.layoutParams

            if (layoutParams !is IHandleAnimation) {
                continue
            }

            var childTop = child.top

            // 滑出去的这一截高度：t
            // child离屏幕顶部的高度
            var absoluteTop = childTop - t
            Log.i(
                TAG,
                "onScrollChanged: childTop=$childTop absoluteTop=$absoluteTop scrollTop=$t height=$height childHeight=${child.height}"
            )
            // 说明child还在屏幕内
            if (absoluteTop <= height) {
                // child可见的高度  = ScrollView的高度 - child离屏幕顶部的高度
                var visibleHeight = height - absoluteTop

                //float ratio = child浮现的高度/child的高度
                // 计算可见的高度与child完整高度的比例
                var ratio = visibleHeight / child.height.toFloat()
                // clamp方法 确保ratio是在0~1的范围
                layoutParams.startAnimation(child, clamp(ratio, 0f, 1f))
            } else {
                // child滑出屏幕了,恢复正常
                layoutParams.resetAnimation(child)
            }
        }
    }

    //求三个数的中间大小的一个数。
    private fun clamp(ratio: Float, minValue: Float, maxValue: Float): Float {
        return max(minValue, min(ratio, maxValue))
    }
}