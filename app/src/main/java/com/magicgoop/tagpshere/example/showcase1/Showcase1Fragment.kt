package com.magicgoop.tagpshere.example.showcase1

import android.graphics.Color
import android.os.Bundle
import android.text.TextPaint
import android.widget.Toast
import com.magicgoop.tagpshere.example.R
import com.magicgoop.tagpshere.example.databinding.FragmentShowcase1Binding
import com.magicgoop.tagpshere.example.util.LoremIpsum
import com.magicgoop.tagsphere.OnTagTapListener
import com.magicgoop.tagsphere.item.TagItem
import com.magicgoop.tagsphere.item.TextTagItem
import com.mozhimen.basick.elemk.androidx.fragment.bases.BaseFragmentVB
import kotlin.random.Random

class Showcase1Fragment : BaseFragmentVB<FragmentShowcase1Binding>() {
    companion object {
        fun newInstance(): Showcase1Fragment =
            Showcase1Fragment()
    }

    override fun initView(savedInstanceState: Bundle?) {
        initTagView()
    }

    private fun initTagView() {
        vb.tagSphereView.setTextPaint(
            TextPaint().apply {
                isAntiAlias = true
                textSize = resources.getDimension(R.dimen.tag_text_size)
                color = Color.DKGRAY
            }
        )
        val loremSize = LoremIpsum.list.size
        (0..40).map {
            TextTagItem(text = LoremIpsum.list[Random.nextInt(loremSize)])
        }.toList().let {
            vb.tagSphereView.addTagList(it)
        }
        vb.tagSphereView.setRadius(3f)
        vb.tagSphereView.setOnTagTapListener(object : OnTagTapListener {
            override fun onTap(tagItem: TagItem) {
                Toast.makeText(
                    requireContext(),
                    "On tap: ${(tagItem as TextTagItem).text}",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
    }
}