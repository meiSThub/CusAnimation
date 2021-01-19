package com.mei.parallel

import android.animation.*
import android.util.Log
import android.view.View
import android.view.animation.LinearInterpolator
import android.widget.HorizontalScrollView
import androidx.viewpager.widget.ViewPager
import com.mei.animation.R
import kotlin.math.abs

/**
 * @date 2021/1/18
 * @author mxb
 * @desc 视差动画
 * @desired
 */
class ParallelTransformer : ViewPager.PageTransformer, ViewPager.OnPageChangeListener {

    private val TAG = "ParallelTransformer"

    // 颜色估值器
    private val argbEvaluator = ArgbEvaluator()

    // 当前选中的页面的编号
    private var mCurrentPageIndex = 0

    // 页面是否真的发生了切换
    private var mPageAlreadyChanged = true // 默认为true，初次显示ViewPager页面的时候，默认也认为是选中了

    // 动画执行的时间
    private val mDuration = 400L

    /**
     * 此方法是滑动的时候每一个页面View都会调用该方法
     *
     * 视差效果：在View正常滑动的情况下，给当前View或者当前view里面的每一个子view来一个加速度
     * 而且每一个加速度大小不一样。
     *
     * @param page      当前的页面
     * @param position  当前滑动的位置，当ViewPager滑动停止的时候，position==0表示当前选中的页面
     */
    override fun transformPage(page: View, position: Float) {

        Log.i(TAG, "transformPage: -------------------start-------------------")
        // 1. 页面的父容器肯定是ViewPager，让ViewPager执行背景色渐变的动画
        var parent = page.parent
        // 背景色
        var green = page.context.resources.getColor(R.color.bg1_green)
        var blue = page.context.resources.getColor(R.color.bg2_blue)


        var pageIndex = page.tag as Int
        Log.i(TAG, "transformPage: pageIndex=$pageIndex position=$position")
        if (pageIndex == mCurrentPageIndex) {
            // 以当前滑动的进度为动画的执行进度
            val fraction = abs(position)
            // 获取父容器的背景色
            var parentBgColor = when (pageIndex) {
                0 -> argbEvaluator.evaluate(fraction, green, blue) as Int
                1 -> argbEvaluator.evaluate(fraction, blue, green) as Int
                2 -> argbEvaluator.evaluate(fraction, green, blue) as Int
                else -> 0
            }
            Log.i(TAG, "transformPage: parentBgColor=$parentBgColor")

            if (parent is ViewPager) {
                Log.i(TAG, "transformPage: parent=$parent")
            }

            // 设置父容器的背景色
            if (parent is View) {
                parent.setBackgroundColor(parentBgColor)
            }
        }

        // 背景容器
        var bgContainer = page.findViewById<View>(R.id.bg_container)

        // 页面的背景
        val bgImageView1 = page.findViewById<View>(R.id.imageView0) // 背景1
        val bgImageView2 = page.findViewById<View>(R.id.imageView0_2) // 背景2

        // 水平滑动的ScrollView
        var scrollView = page.findViewById<HorizontalScrollView>(R.id.horizontal_scroll_view)

        // 2. 当ViewPager滑动结束的时候，即页面选中的了，执行页面内部背景切换动画
        if (position == 0f) {// 当position==0，表示这个page页面是被选中的页面
            // 增加 mPageAlreadyChanged 判断的原因：避免只滑动了一点点，但没有发生页面的切换，即滑动了一点之后，
            // 距离不够大，导致页面没有发生切换这种情况下，就不执行页面背景的平移动画了
            Log.i(TAG, "transformPage: position=$position mPageAlreadyChanged=$mPageAlreadyChanged")
            if (mPageAlreadyChanged) {

                val animatorSet = AnimatorSet()
                Log.i(
                    TAG,
                    "transformPage: 执行动画前 pageIndex=$pageIndex bg1TranslationX=${bgImageView1.translationX} bg2TranslationX=${bgImageView2.translationX}"
                )

                // 当页面选中了，执行页面内部背景切换动画
                var translation1 = ObjectAnimator.ofFloat(
                    bgImageView1,
                    "translationX",
                    0f,
                    -bgImageView1.width.toFloat()
                )
                translation1.duration = mDuration
                translation1.interpolator = LinearInterpolator()
                translation1.addUpdateListener {
                    // 在执行背景平移的时候，同时也滚动ScrollView里面的内容
                    var fraction = it.animatedFraction
                    bgImageView2.visibility = View.VISIBLE
                    scrollView.smoothScrollTo((scrollView.width * fraction).toInt(), 0)
                }

                Log.i(TAG, "transformPage: width=${bgImageView2.width.toFloat()}")

                var translation2 = ObjectAnimator.ofFloat(
                    bgImageView2,
                    "translationX",
                    bgImageView2.width.toFloat(),
                    0f
                )
                translation2.duration = mDuration
                translation2.interpolator = LinearInterpolator()

                animatorSet.playTogether(translation1, translation2)
                animatorSet.startDelay = 200
                animatorSet.addListener(object : AnimatorListenerAdapter() {
                    override fun onAnimationEnd(animation: Animator?) {
                        super.onAnimationEnd(animation)
                        Log.i(
                            TAG,
                            "transformPage: 动画结束前：pageIndex=$pageIndex bg1TranslationX=${bgImageView1.translationX} bg2TranslationX=${bgImageView2.translationX}"
                        )
                    }
                })
                animatorSet.start()

                // 动画执行完，恢复状态
                mPageAlreadyChanged = false
            }
        } else if (position >= 1f || position <= -1f) {
            Log.i(
                TAG,
                "transformPage: 动画复原前：pageIndex=$pageIndex bg1TranslationX=${bgImageView1.translationX} bg2TranslationX=${bgImageView2.translationX}"
            )
            // 3. 复原所有的动画
            bgImageView1.translationX = 0f
            bgImageView2.translationX = 0f
            bgImageView2.visibility = View.INVISIBLE
            scrollView.smoothScrollTo(0, 0)

            Log.i(
                TAG,
                "transformPage: 动画复原后：pageIndex=$pageIndex bg1TranslationX=${bgImageView1.translationX} bg2TranslationX=${bgImageView2.translationX}"
            )
        } else if (position < 1 && position > -1) { //(-1~1)
            // 4. 滑动页面的时候，执行页面背景的旋转动画，为了让页面之间的切换更加无缝衔接，
            // 这里让每个页面内部的背景来执行旋转动画，而页面本身就还是保持ViewPager默认的切换动画，
            // 这样就可以看到一个页面之间的动画有一个衔接的效果

            Log.i(TAG, "transformPage: 执行旋转动画 pageIndex=$pageIndex position=$position")
            // 在滑动切换页面的过程中，执行页面的旋转动画
            // 以(width/2,height)为轴点进行旋转
            bgContainer.pivotX = bgContainer.width * 0.5f
            bgContainer.pivotY = page.height.toFloat()
            bgContainer.rotation = 45 * position // 围绕z轴旋转

            scrollView.pivotX = scrollView.width * 0.5f
            scrollView.pivotY = scrollView.height.toFloat()
            scrollView.rotation = 45 * position

            var phone = page.findViewById<View>(R.id.iv_phone_bg)
            phone.pivotX = phone.width * 0.5f
            phone.pivotY = phone.height.toFloat()
            phone.rotation = 45 * position
        }
    }

    override fun onPageScrollStateChanged(state: Int) {

    }

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
    }

    override fun onPageSelected(position: Int) {
        mCurrentPageIndex = position
        mPageAlreadyChanged = true
        Log.i(TAG, "onPageSelected: 当前选中的页面是：$position")
    }

}