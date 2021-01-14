package com.mei.animation

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity

/**
 * @date 2021/1/9
 * @author mxb
 * @desc 动画入口类
 * @desired
 */
class AnimationMainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_animation_main)
    }

    fun simpleViewAnimation(view: View) {
        startActivity(Intent(this, ViewAnimationActivity::class.java))
    }

    fun layoutAnimation(view: View) {
        startActivity(Intent(this, LayoutAnimationActivity::class.java))
        overridePendingTransition(R.anim.enter_anim, R.anim.exit_anim)
    }

    fun propertyAnimation(view: View) {
        startActivity(Intent(this, PropertyAnimationActivity::class.java))
    }

    override fun finish() {
        super.finish()
        overridePendingTransition(R.anim.enter_anim, R.anim.exit_anim)
    }

    fun evaluator(view: View) {
        startActivity(Intent(this, EvaluatorActivity::class.java))
    }

    fun interpolator(view: View) {
        startActivity(Intent(this, InterpolatorActivity::class.java))
    }

    fun flower(view: View) {
        startActivity(Intent(this, FlowerAnimationActivity::class.java))
    }

    fun taobao(view: View) {
        startActivity(Intent(this, TaoBaoDetailActivity::class.java))
    }
}