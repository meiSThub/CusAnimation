package com.mei.animation.viewpager3danimation

import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.ViewPager

/**
 * @date 2021/1/18
 * @author mxb
 * @desc 视差动画，每个页面里面的子View，在滑动的时候，都会执行动画，形成视觉差异
 * @desired
 */
class PageChildTransformer : ViewPager.PageTransformer {

    /**
     * position 取值范围 -1～1，
     */
    override fun transformPage(page: View, position: Float) {
        // 在1和-1的时候，就不管了
        if (position < 1 && position > -1 && page is ViewGroup) {
            var childCount = page.childCount

            for (i in 0 until childCount) {
                var child = page.getChildAt(i)

                // 随机数，是为了让每个子View平移的距离都不一样，这样产生的视差效果更加自然和随意
                var fraction = Math.random() * 3

                if (child.tag == null) {
                    child.tag = fraction
                } else {
                    fraction = child.tag as Double
                }

                child.translationX = child.width * position * fraction.toFloat()
            }
        }
    }
}