package com.mei.animation.viewpager3danimation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import com.mei.animation.R

/**
 * @date 2021/1/18
 * @author mxb
 * @desc ViewPager的3D动画实现
 * @desired
 */
class ViewPager3DAnimationActivity : AppCompatActivity() {

    private var viewPager: ViewPager? = null

    private var layoutIds = intArrayOf(R.layout.welcome1, R.layout.welcome2, R.layout.welcome3)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_viewpager_3d_animation)
        viewPager = findViewById(R.id.view_pager)
        viewPager?.setPageTransformer(true, WelcomePageTransformer())
        viewPager?.adapter = object : FragmentPagerAdapter(supportFragmentManager) {
            override fun getItem(position: Int): Fragment {
                val fragment = TranslateFragment()
                val args = Bundle()
                args.putInt("layoutId", layoutIds[position])
                fragment.arguments = args
                return fragment
            }

            override fun getCount(): Int {
                return layoutIds.size
            }
        }
    }

}