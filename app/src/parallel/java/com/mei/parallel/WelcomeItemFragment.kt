package com.mei.parallel

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

/**
 * @date 2021/1/18
 * @author mxb
 * @desc 平行空间效果的引导页
 * @desired
 */
class WelcomeItemFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val layoutId = arguments!!.getInt("layoutId")
        val pageIndex = arguments!!.getInt("pageIndex")
        val view = inflater.inflate(layoutId, container, false)
        view.tag = pageIndex

        return view
    }
}