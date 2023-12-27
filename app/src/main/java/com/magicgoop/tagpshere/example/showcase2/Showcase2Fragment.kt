package com.magicgoop.tagpshere.example.showcase2

import android.os.Bundle
import com.magicgoop.tagpshere.example.R
import com.magicgoop.tagpshere.example.databinding.FragmentShowcase2Binding
import com.mozhimen.basick.elemk.androidx.fragment.bases.BaseFragmentVB

class Showcase2Fragment : BaseFragmentVB<FragmentShowcase2Binding>() {
    companion object {
        fun newInstance(): Showcase2Fragment =
            Showcase2Fragment()
    }

    override fun initView(savedInstanceState: Bundle?) {
        (0..250).map {
            DotTagItem(resources.getDimension(R.dimen.dot_radius_2))
        }.toList().let {
            vb.tagSphere1.addTagList(it)
        }
    }

    override fun onResume() {
        super.onResume()
        vb.tagSphere1.startAutoRotation(-1f, 1f)
    }

    override fun onPause() {
        super.onPause()
        vb.tagSphere1.stopAutoRotation()
    }
}