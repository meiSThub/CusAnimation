package com.mei.animation.viewpager3danimation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

/**
 * @date 2021/1/18
 * @author mxb
 * @desc
 * @desired
 */
class TranslateFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var layoutId = arguments?.getInt("layoutId") ?: 0
        return inflater.inflate(layoutId, container, false)
    }
}