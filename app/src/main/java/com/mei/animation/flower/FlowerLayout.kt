package com.mei.animation.flower

import android.animation.*
import android.annotation.SuppressLint
import android.content.Context
import android.graphics.PointF
import android.util.AttributeSet
import android.util.Log
import android.view.animation.*
import android.widget.ImageView
import android.widget.RelativeLayout
import com.mei.animation.R
import java.util.*

/**
 * @date 2021/1/13
 * @author mxb
 * @desc 自定义一个刷鲜花的布局
 * @desired
 */
class FlowerLayout(context: Context?, attrs: AttributeSet?) : RelativeLayout(context, attrs) {

    // 定义插值器
    private var linearInterpolator = LinearInterpolator()
    private var accelerateInterpolator = AccelerateInterpolator()
    private var decelerateInterpolator = DecelerateInterpolator()
    private var accelerateDecelerateInterpolator = AccelerateDecelerateInterpolator()
    private var interpolators: Array<Interpolator> = arrayOf(
        linearInterpolator,
        accelerateInterpolator,
        decelerateInterpolator,
        accelerateDecelerateInterpolator
    )

    // 初始化照片
    @SuppressLint("UseCompatLoadingForDrawables")
    private var drawables = arrayOf(
        resources.getDrawable(R.drawable.red),
        resources.getDrawable(R.drawable.yellow),
        resources.getDrawable(R.drawable.blue)
    )

    private var layoutParams: LayoutParams? = null
    private var drawableWidth = 0
    private var drawableHeight = 0

    private var random = Random()

    // 初始化块
    init {
        var drawable = drawables[0]
        drawableWidth = drawable.intrinsicWidth
        drawableHeight = drawable.intrinsicHeight
        layoutParams = LayoutParams(drawableWidth, drawableHeight)
        layoutParams?.addRule(CENTER_HORIZONTAL)
        layoutParams?.addRule(ALIGN_PARENT_BOTTOM)
    }

    // 次构造函数
    constructor(context: Context?) : this(context, null)

    fun addFlower() {
        // 添加心形，并开始执行动画
        var imageView = ImageView(context)
        imageView.setImageDrawable(drawables[random.nextInt(3)])
        //将iv添加到父容器底部、水平居中位置
        imageView.layoutParams = layoutParams
        addView(imageView)

        // 执行动画
        startAnimation(imageView)
    }

    private fun startAnimation(imageView: ImageView) {
        var animator = getAnimator(imageView)
        animator.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator?) {
                super.onAnimationEnd(animation)
                removeView(imageView)
            }
        })
        animator.start()
    }

    private fun getAnimator(imageView: ImageView): AnimatorSet {
        // 1. 透明度变化
        var alphaAnimator = ObjectAnimator.ofFloat(imageView, "alpha", 0.3f, 1f)
        alphaAnimator.duration = 300

        // 2. 宽度缩放,从 0.3 放大到1
        var scaleXAnimator = ObjectAnimator.ofFloat(imageView, "scaleX", 0.3f, 1f)
        scaleXAnimator.duration = 300

        // 3. 高度缩放，从0.3放大到1
        var scaleYAnimator = ObjectAnimator.ofFloat(imageView, "scaleY", 0.3f, 1f)
        scaleYAnimator.duration = 300

        // 把上面三个动画一起执行
        var enterAnimator = AnimatorSet()
        enterAnimator.playTogether(alphaAnimator, scaleXAnimator, scaleYAnimator)
//        animator.play(alphaAnimator).with(scaleXAnimator).with(scaleXAnimator)

        // 总的动画集合
        var animator = AnimatorSet()

        //设置平移的曲线动画---贝塞尔曲线
        val bezierAnimator = getBezierValueAnimator(imageView)
        // 按顺序执行动画
        animator.playSequentially(enterAnimator, bezierAnimator)
        // 加速因子，使用插值器
        animator.interpolator = interpolators[random.nextInt(4)]

        return animator
    }

    /**
     * 执行向上票的动画
     *
     * 得到一个贝塞尔曲线动画
     */
    private fun getBezierValueAnimator(imageView: ImageView): ValueAnimator {
        //根据贝塞尔公式确定四个点（起始点p0，拐点1p1，拐点2p2，终点p3）
        var pointF0 = PointF(
            (measuredWidth - drawableWidth) / 2f,
            (measuredHeight - drawableHeight).toFloat()
        )
        // 拐点1
        var pointF1 = getPointF(1)
        // 拐点2
        var pointF2 = getPointF(2)

        // 终点
        var pointF3 = PointF(random.nextInt(measuredWidth).toFloat(), 0f)
        // 估值器Evaluator,来控制view的行驶路径(不断地修改point.x,point.y)
        var bezierEvaluator = BezierEvaluator(pointF1, pointF2)

        var valueAnimator = ValueAnimator.ofObject(bezierEvaluator, pointF0, pointF3)
        valueAnimator.duration = 4000

        valueAnimator.addUpdateListener {
            var pointF = it.animatedValue as PointF
            Log.i("FlowerLayout", "getBezierValueAnimator: x=${pointF.x};y=${pointF.y}")
            imageView.x = pointF.x
            imageView.y = pointF.y
            imageView.alpha = 1 - it.animatedFraction
        }

        valueAnimator.setTarget(imageView)
        return valueAnimator
    }

    private fun getPointF(i: Int): PointF {
        var pointF = PointF()
        pointF.x = random.nextInt(measuredWidth).toFloat()
        Log.i("getPointF", "getPointF: x=${pointF.x};halfWidth=${measuredWidth / 2}")
        if (i == 1) {
            pointF.y = (random.nextInt(measuredHeight / 2) + measuredHeight / 2).toFloat()
        } else {
            pointF.y = random.nextInt(measuredHeight / 2).toFloat()
        }
        return pointF

    }
}