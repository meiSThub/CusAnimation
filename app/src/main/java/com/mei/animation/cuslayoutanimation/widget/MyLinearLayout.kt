package com.mei.animation.cuslayoutanimation.widget

import android.animation.ArgbEvaluator
import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
import com.mei.animation.R

/**
 * @date 2021/1/17
 * @author mxb
 * @desc 自定义LinearLayout，主要是为了解析自定义属性
 *
 * 因为要想解析得到自定义的属性，有两个途径可以做到：
 * 1. 通过自定义View，在自定义的View里面解析自定义的属性
 * 2. 通过自定义容器View，借助LayoutParams来拿到自定义属性
 *
 * 第二种方式相对来说比较友好一些，因为这样不用去自定义每一个系统控件了，只需要完成有限的容器控件的自定义就可以做到了
 * 这样使用者继续使用系统控件都没有关系了，只要用了我们自定义的容器组件，我们就可以拿到自定义属性了
 *
 * @desired
 */
class MyLinearLayout(context: Context?, attrs: AttributeSet?) : LinearLayout(context, attrs) {

    init {
        orientation = VERTICAL
    }

    /**
     * 在解析xml布局文件的时候，会调用次方法生成一个LayoutParams对象，重写此方法，让生成的LayoutParams为自定定义的MyLayoutParams对象
     */
    override fun generateLayoutParams(attrs: AttributeSet?): LayoutParams {
        return MyLayoutParams(context, attrs)
    }

    /**
     * 自定义LayoutParams类，为了解析自定义属性
     *
     * <declare-styleable name="DiscrollView_LayoutParams">
     * <attr name="discrollve_alpha" format="boolean" />
     * <attr name="discrollve_scaleX" format="boolean" />
     * <attr name="discrollve_scaleY" format="boolean" />
     * <attr name="discrollve_fromBgColor" format="color" />
     * <attr name="discrollve_toBgColor" format="color" />
     * <attr name="discrollve_translation" format="enum">
     * <flag name="fromTop" value="0x01" />
     * <flag name="fromBottom" value="0x02" />
     * <flag name="fromLeft" value="0x04" />
     * <flag name="fromRight" value="0x08" />
     * </attr>
     * </declare-styleable>
     */
    @SuppressLint("CustomViewStyleable")
    class MyLayoutParams(c: Context?, attrs: AttributeSet?) : LinearLayout.LayoutParams(c, attrs),
        IHandleAnimation {

        //保存自定义属性
        //定义很多的自定义属性
        //保存自定义属性
        //定义很多的自定义属性
        /**
         * <attr name="discrollve_translation">
         * <flag name="fromTop" value="0x01"></flag>
         * <flag name="fromBottom" value="0x02"></flag>
         * <flag name="fromLeft" value="0x04"></flag>
         * <flag name="fromRight" value="0x08"></flag>
        </attr> *
         * 0000000001
         * 0000000010
         * 0000000100
         * 0000001000
         * top|left
         * 0000000001 top
         * 0000000100 left 或运算 |
         * 0000000101
         * 反过来就使用& 与运算
         */
        private val TRANSLATION_FROM_TOP = 0x01
        private val TRANSLATION_FROM_BOTTOM = 0x02
        private val TRANSLATION_FROM_LEFT = 0x04
        private val TRANSLATION_FROM_RIGHT = 0x08

        var discrollveAlpha = false // 是否执行透明度动画
        var discrollveScaleX = false // 是否指定水平缩放动画
        var discrollveScaleY = false // 是否执行垂直缩放动画
        var discrollveFromBgColor = 0 // 背景色渐变的起始颜色值
        var discrollveToBgColor = 0 // 背景色渐变的结束颜色值
        var discrollveTranslation = -1 // 平移动画类型

        // 颜色估值器
        var argbEvaluator = ArgbEvaluator()

        init {
            var typedArray = c?.obtainStyledAttributes(attrs, R.styleable.DiscrollView_LayoutParams)

            discrollveAlpha = typedArray?.getBoolean(
                R.styleable.DiscrollView_LayoutParams_discrollve_alpha,
                false
            ) ?: false
            discrollveScaleX = typedArray?.getBoolean(
                R.styleable.DiscrollView_LayoutParams_discrollve_scaleX,
                false
            ) ?: false

            discrollveScaleY = typedArray?.getBoolean(
                R.styleable.DiscrollView_LayoutParams_discrollve_scaleY,
                false
            ) ?: false

            discrollveFromBgColor = typedArray?.getColor(
                R.styleable.DiscrollView_LayoutParams_discrollve_fromBgColor,
                -1
            ) ?: -1

            discrollveToBgColor =
                typedArray?.getColor(R.styleable.DiscrollView_LayoutParams_discrollve_toBgColor, -1)
                    ?: -1

            discrollveTranslation =
                typedArray?.getInt(R.styleable.DiscrollView_LayoutParams_discrollve_translation, -1)
                    ?: -1

            typedArray?.recycle()
        }

        override fun startAnimation(view: View, ratio: Float) {

            if (discrollveAlpha) {
                view.alpha = ratio
            }

            if (discrollveScaleX) {
                view.scaleX = ratio
            }

            if (discrollveScaleY) {
                view.scaleY = ratio
            }

            if (discrollveFromBgColor != -1 && discrollveToBgColor != -1) {
                var backgroundColor: Int =
                    argbEvaluator.evaluate(ratio, discrollveFromBgColor, discrollveToBgColor) as Int
                view.setBackgroundColor(backgroundColor)
            }

            // 平移动画
            if (isTranslationFrom(TRANSLATION_FROM_TOP)) {
                view.translationY = -view.height * (1 - ratio) //height--->0(0代表恢复到原来的位置)
            }

            if (isTranslationFrom(TRANSLATION_FROM_BOTTOM)) {
                view.translationY = view.height * (1 - ratio)//-height--->0(0代表恢复到原来的位置)
            }

            if (isTranslationFrom(TRANSLATION_FROM_LEFT)) {
                view.translationX = -view.width * (1 - ratio)//mWidth--->0(0代表恢复到本来原来的位置)
            }

            if (isTranslationFrom(TRANSLATION_FROM_RIGHT)) {
                view.translationX = view.width * (1 - ratio) // mWidth--->0(0代表恢复到本来原来的位置)
            }
        }

        override fun resetAnimation(view: View) {

            if (discrollveAlpha) {
                view.alpha = 0f
            }

            if (discrollveScaleX) {
                view.scaleX = 0f
            }

            if (discrollveScaleY) {
                view.scaleY = 0f
            }

            //平移动画  int值：left,right,top,bottom    left|bottom
            // 平移动画
            if (isTranslationFrom(TRANSLATION_FROM_TOP)) {
                view.translationY = -view.height.toFloat() //height--->0(0代表恢复到原来的位置)
            }

            if (isTranslationFrom(TRANSLATION_FROM_BOTTOM)) {
                view.translationY = view.height.toFloat() //-height--->0(0代表恢复到原来的位置)
            }

            if (isTranslationFrom(TRANSLATION_FROM_LEFT)) {
                view.translationX = -view.width.toFloat() //mWidth--->0(0代表恢复到本来原来的位置)
            }

            if (isTranslationFrom(TRANSLATION_FROM_RIGHT)) {
                view.translationX = view.width.toFloat()  // mWidth--->0(0代表恢复到本来原来的位置)
            }
        }

        /**
         * 判断平移的值里面是否包含指定的方向
         */
        fun isTranslationFrom(translationMask: Int): Boolean {
            if (discrollveTranslation == -1) {
                return false
            }

            return (discrollveTranslation and translationMask) == translationMask
        }
    }
}