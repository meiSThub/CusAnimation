package com.mei.animation

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity

/**
 * @date 2021/1/14
 * @author mxb
 * @desc 仿淘宝详情页动画
 * @desired
 */
class TaoBaoDetailActivity : AppCompatActivity() {

    private val TAG = "TaoBaoDetailActivity"

    private var imageView1: ImageView? = null
    private var imageView2: View? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tao_bao_detail)
        imageView1 = findViewById(R.id.image1)
        imageView2 = findViewById(R.id.image2)
    }

    /**
     * 点击购买按钮
     */
    fun buy(view: View) {
        // 先围绕x轴旋转
        var rotationX = ObjectAnimator.ofFloat(view, "rotationX", 0f, 30f)
        rotationX.duration = 300

        // 透明度
        var alpha = ObjectAnimator.ofFloat(view, "alpha", 1f, 0.8f)
        alpha.duration = 300

        // xy缩放
        var scaleX = ObjectAnimator.ofFloat(view, "scaleX", 1f, 0.8f)
        scaleX.duration = 300
        var scaleY = ObjectAnimator.ofFloat(view, "scaleY", 1f, 0.8f)
        scaleY.duration = 300

        // 恢复回来
        var rotationXResume = ObjectAnimator.ofFloat(view, "rotationX", 30f, 0f)
        rotationXResume.duration = 300
        rotationXResume.startDelay = 300

        // 透明度
        var alphaResume = ObjectAnimator.ofFloat(view, "alpha", 0.8f, 1f)
        alphaResume.duration = 300
        alphaResume.startDelay = 300

        // 向上平移
        var translationY =
            ObjectAnimator.ofFloat(view, "translationY", 0f, -view.measuredHeight * 0.1f)
        translationY.duration = 300

        var translationY2 = ObjectAnimator.ofFloat(
            imageView2!!,
            "translationY",
            imageView2!!.bottom.toFloat(),
            imageView2!!.top.toFloat()
        )
        translationY2.duration = 400
        translationY2.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationStart(animation: Animator?) {
                super.onAnimationStart(animation)
                imageView2?.visibility = View.VISIBLE
            }
        })

        // 动画集合
        var animator = AnimatorSet()
        animator.playTogether(
            rotationX,
            alpha,
            scaleX,
            scaleY,
            rotationXResume,
            alphaResume,
            translationY,
            translationY2
        )

        animator.start()
    }

    /**
     * 加入购物车，恢复动画
     */
    fun add(view: View) {

        var scaleX = ObjectAnimator.ofFloat(imageView1!!, "scaleX", 0.8f, 1f)
        scaleX.duration = 300
        var scaleY = ObjectAnimator.ofFloat(imageView1!!, "scaleY", 0.8f, 1f)
        scaleY.duration = 300
        var translationY = imageView1!!.translationY
        Log.i(TAG, "add: translationY=$translationY")
        var translationYAnimator =
            ObjectAnimator.ofFloat(imageView1!!, "translationY", translationY, 0f)
        translationYAnimator.duration = 300

        var translationYAnimation2 =
            ObjectAnimator.ofFloat(view, "translationY", view.top.toFloat(), view.bottom.toFloat())
        translationYAnimation2.duration = 300

        var animatorSet = AnimatorSet()
        animatorSet.playTogether(scaleX, scaleY, translationYAnimator, translationYAnimation2)
        animatorSet.start()

    }
}