package com.mei.parallel

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentPagerAdapter
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.viewpager.widget.ViewPager
import com.mei.animation.R

/**
 * @date 2021/1/18
 * @author mxb
 * @desc 平行空间动画，视差动画
 * @desired
 */
class ParallelActivity : AppCompatActivity() {

    private var viewPager: ViewPager? = null

    private var layoutIds = intArrayOf(
        R.layout.welcome_item_1,
        R.layout.welcome_item_2,
        R.layout.welcome_item_3
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_parallel)

        viewPager = findViewById(R.id.view_pager)

        viewPager?.adapter = object : FragmentPagerAdapter(supportFragmentManager) {
            override fun getItem(position: Int): Fragment {
                var fragment = WelcomeItemFragment()
                var args = Bundle()
                args.putInt("layoutId", layoutIds[position])
                args.putInt("pageIndex", position)
                fragment.arguments = args
                return fragment
            }

            override fun getCount(): Int {
                return layoutIds.size
            }
        }

        var transformer = ParallelTransformer()

        // 为ViewPager设置转换器，方便为每个页面添加动画
        viewPager?.setPageTransformer(true, transformer)

        // 为ViewPager设置页面切换观察者
        viewPager?.addOnPageChangeListener(transformer)

        viewPager?.postDelayed({
            Log.i("onCreateTAG", "onCreate: width=${viewPager?.measuredWidth}")
        },100)
    }

}