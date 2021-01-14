package com.mei.animation

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.mei.animation.flower.FlowerLayout

/**
 * @date 2021/1/13
 * @author mxb
 * @desc 实现刷鲜花的动画
 * @desired
 */
class FlowerAnimationActivity : AppCompatActivity() {

    private var flowerLayout: FlowerLayout? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_flower_animation)

        flowerLayout = findViewById(R.id.flower_layout)
    }

    fun good(view: View) {
        flowerLayout?.addFlower()
    }
}