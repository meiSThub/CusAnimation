package com.mei.animation

import android.animation.ObjectAnimator
import android.animation.TypeEvaluator
import android.animation.ValueAnimator
import android.graphics.PointF
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.mei.animation.wrapper.ViewWrapper

/**
 * @date 2021/1/12
 * @author mxb
 * @desc 自定义估值器,实现抛物线平移
 * @desired
 */
class EvaluatorActivity : AppCompatActivity() {

    private val TAG = "EvaluatorActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_evaluator)
    }

    fun evaluator(view: View) {

        var animator = ObjectAnimator()
        animator.duration = 400
        animator.setObjectValues(PointF(0f, 0f))
        val pointF = PointF()

        animator.setEvaluator(object : TypeEvaluator<PointF> {

            override fun evaluate(fraction: Float, startValue: PointF?, endValue: PointF?): PointF {
                // 实现具体的估值算法
                // 因为要计算抛物线的轨迹，这里就模仿一个有水平速度的自由落体的物体运动轨迹

                var time = fraction * 5f
                var x = startValue?.x ?: 0 + time * 100 // 假设水平速度为100
                var y = startValue?.y ?: 0 + 9.8f * time * time * 2
                Log.i(TAG, "evaluate: x=$x;y=$y ;fraction=$fraction")
                pointF.x = x
                pointF.y = y
                return pointF
            }
        })
        animator.addUpdateListener {
            var pointF: PointF = it.animatedValue as PointF
            view.translationX = pointF.x
            view.translationY = pointF.y
        }
        animator.start()
    }

    /**
     * 通过一个包装类，实现set和get方法，从而实现属性动画
     */
    fun viewWrapper(view: View) {
        var measureWidth = view.measuredWidth
        var wrapper = ViewWrapper(view)
        var objectAnimator =
            ObjectAnimator.ofInt(wrapper, "width", measureWidth, measureWidth + 200)
        objectAnimator.duration = 300
        objectAnimator.start()
    }

    /**
     * 通过ValueAnimator实现属性动画
     */
    fun valueAnimator(view: View) {
        var measureWidth = view.measuredWidth
        var valueAnimator = ValueAnimator.ofInt(measureWidth, measureWidth + 200)
        valueAnimator.duration = 300
        valueAnimator.addUpdateListener {
            var width = it.animatedValue as Int
            var layoutParams = view.layoutParams
            layoutParams.width = width
            view.layoutParams = layoutParams
        }
        valueAnimator.start()
    }
}