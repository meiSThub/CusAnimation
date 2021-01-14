package com.mei.animation.flower

import android.animation.TypeEvaluator
import android.graphics.PointF
import android.util.Log

/**
 * @date 2021/1/13
 * @author mxb
 * @desc 贝塞尔曲线类型估值器
 * @desired
 */
class BezierEvaluator(private var pointF1: PointF, private var pointF2: PointF) :
    TypeEvaluator<PointF> {

    private var pointF = PointF()

    override fun evaluate(t: Float, startValue: PointF, endValue: PointF): PointF {
        //t百分比：0~1
        // b(t)=p0*(1-t)*(1-t)*(1-t)+3*p1*t*(1-t)*(1-t)+3*p2*t*t*(1-t)+p3*t*t*t
        pointF.x =
            startValue.x * (1 - t) * (1 - t) * (1 - t) + 3 * pointF1.x * t * (1 - t) * (1 - t) + 3 * pointF2.x * t * t * (1 - t) + endValue.x * t * t * t

        pointF.y =
            startValue.y * (1 - t) * (1 - t) * (1 - t) + 3 * pointF1.y * t * (1 - t) * (1 - t) + 3 * pointF2.y * t * t * (1 - t) + endValue.y * t * t * t

        Log.i(
            "BezierEvaluator",
            "evaluate: t=$t x=${pointF.x} y=${pointF.y} endX=${endValue.x} endY=${endValue.y}"
        )

        return pointF
    }
}