package com.mei.animation.viewpager3danimation

import android.util.Log
import android.view.View
import androidx.viewpager.widget.ViewPager
import kotlin.math.abs
import kotlin.math.max

/**
 * @date 2021/1/18
 * @author mxb
 * @desc ViewPager实现3D动画的实现类，ViewPager提供了转换的接口
 * @desired
 */
class WelcomePageTransformer : ViewPager.PageTransformer {

    private val TAG = "WelcomePageTransformer"

    /**
     * 此方法是滑动的时候每一个页面View都会调用该方法
     * view:当前的页面
     * position:当前滑动的位置
     * 视差效果：在View正常滑动的情况下，给当前View或者当前view里面的每一个子view来一个加速度
     * 而且每一个加速度大小不一样。
     * -1~0
     * 0~1
     */
    override fun transformPage(page: View, position: Float) {
        Log.i(TAG, "transformPage: position=$position")

        // 效果1
//        page.scaleX = 1 - abs(position)
//        page.scaleY = 1 - abs(position)

        // 效果2
//        page.scaleX = max(0.9f, 1 - abs(position))
//        page.scaleY = max(0.9f, 1 - abs(position))

        // 效果3 3D翻转
//        page.pivotX = if (position < 0) page.width.toFloat() else 0f
//        page.pivotY = 0.5f * page.height
//        page.rotationY = position * 45

        // 效果4，3D内翻转
//        page.pivotX = if (position < 0) page.width.toFloat() else 0f
//        page.pivotY = 0.5f * page.height
//        page.rotationY = -position * 45
//        page.scaleX = max(0.8f, 1 - abs(position))
//        page.scaleY = max(0.8f, 1 - abs(position))

        // 效果5，3D内翻转
        page.pivotX = 0.5f * page.width
        page.pivotY = 0.5f * page.height
        page.rotationY = -position * 45

    }
}