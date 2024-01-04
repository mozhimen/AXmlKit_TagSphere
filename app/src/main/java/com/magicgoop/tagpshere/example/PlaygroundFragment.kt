package com.magicgoop.tagpshere.example

import android.os.Bundle
import android.text.TextPaint
import android.util.Log
import android.widget.SeekBar
import com.google.android.material.snackbar.Snackbar
import com.magicgoop.tagpshere.example.databinding.FragmentPlaygroundBinding
import com.magicgoop.tagpshere.example.util.CustomOnSeekBarChangeListener
import com.magicgoop.tagpshere.example.util.EmojiConstants
import com.magicgoop.tagsphere.OnTagLongPressedListener
import com.magicgoop.tagsphere.OnTagTapListener
import com.magicgoop.tagsphere.item.TagItem
import com.magicgoop.tagsphere.item.TextTagItem
import com.magicgoop.tagsphere.utils.EasingFunction
import com.mozhimen.basick.elemk.androidx.appcompat.bases.databinding.BaseActivityVB
import com.mozhimen.basick.elemk.androidx.fragment.bases.BaseFragmentVB
import kotlin.random.Random

class PlaygroundFragment : BaseFragmentVB<FragmentPlaygroundBinding>(), OnTagLongPressedListener, OnTagTapListener {

    companion object {
        fun newInstance(): PlaygroundFragment = PlaygroundFragment()

        private const val MIN_SENSITIVITY = 1
        private const val MIN_RADIUS = 10f
    }

    override fun initView(savedInstanceState: Bundle?) {
        initTagView()
        initSettings()
    }

    private fun initTagView() {
        val samples = EmojiConstants.emojiCodePoints.size - 1
        vb.tagView.setTextPaint(
            TextPaint().apply {
                isAntiAlias = true
                textSize = resources.getDimension(R.dimen.tag_text_size)
            }
        )
        (0..100).map {
            TextTagItem(
                text = String(
                    Character.toChars(EmojiConstants.emojiCodePoints[Random.nextInt(samples)])
                )
            )
        }.toList().let {
            vb.tagView.addTagList(it)
        }
        vb.tagView.setOnLongPressedListener(this)
        vb.tagView.setOnTagTapListener(this)
    }

    private fun initSettings() {
        vb.sbRadius.setOnSeekBarChangeListener(object : CustomOnSeekBarChangeListener() {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                vb.tagView.setRadius(((progress + MIN_RADIUS) / 10f).also { Log.d(TAG, "onProgressChanged: setRadius $it") })
            }
        })
        vb.sbTouchSensitivity.setOnSeekBarChangeListener(object : CustomOnSeekBarChangeListener() {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                vb.tagView.setTouchSensitivity((progress + MIN_SENSITIVITY).also { Log.d(TAG, "onProgressChanged: setTouchSensitivity $it") })
            }
        })
        vb.cbRotateOnTouch.setOnCheckedChangeListener { _, isChecked ->
            vb.tagView.rotateOnTouch(isChecked)
            if (isChecked) {
                vb.cbAutoRotate.isChecked = false
                vb.tagView.stopAutoRotation()
            }
        }
        vb.cbAutoRotate.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                vb.cbRotateOnTouch.isChecked = false
                val multiplier = Random.nextInt(1, 5)
                vb.tagView.startAutoRotation(
                    Random.nextFloat() * multiplier,
                    -Random.nextFloat() * multiplier
                )
            } else {
                vb.cbRotateOnTouch.isChecked = true
            }
        }

        vb.rgEasingFunctions.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.rbEaseInExpo -> vb.tagView.setEasingFunction { EasingFunction.easeInExpo(it) }
                R.id.rbEaseOutExpo -> vb.tagView.setEasingFunction { EasingFunction.easeOutExpo(it) }
                R.id.rbCustom -> vb.tagView.setEasingFunction { t -> 1f - t * t * t * t * t }
                else -> vb.tagView.setEasingFunction(null)

            }
        }
    }

    override fun onLongPressed(tagItem: TagItem) {
        Snackbar
            .make(vb.root, "onLongPressed: " + (tagItem as TextTagItem).text, Snackbar.LENGTH_LONG)
            .setAction("Delete tag") { vb.tagView.removeTag(tagItem) }
            .show()
    }

    override fun onTap(tagItem: TagItem) {
        Snackbar
            .make(vb.root, "onTap: " + (tagItem as TextTagItem).text, Snackbar.LENGTH_SHORT)
            .show()
    }
}