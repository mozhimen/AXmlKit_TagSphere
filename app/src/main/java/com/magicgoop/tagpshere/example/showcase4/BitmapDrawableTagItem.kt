package com.magicgoop.tagpshere.example.showcase4

import android.graphics.Canvas
import android.graphics.Rect
import android.graphics.drawable.BitmapDrawable
import android.text.TextPaint
import com.magicgoop.tagsphere.item.TagItem
import kotlin.math.max
import kotlin.math.min
import kotlin.math.roundToInt

class BitmapDrawableTagItem(private val bitmapDrawable: BitmapDrawable) : TagItem() {

    init {
        bitmapDrawable.bounds = Rect(0, 0, bitmapDrawable.intrinsicWidth, bitmapDrawable.intrinsicHeight)
    }

    override fun drawSelf(
        x: Float,
        y: Float,
        canvas: Canvas,
        paint: TextPaint,
        easingFunction: ((t: Float) -> Float)?
    ) {
        canvas.save()
        canvas.translate(x - bitmapDrawable.intrinsicWidth / 2f, y - bitmapDrawable.intrinsicHeight / 2f)
        val alpha = easingFunction?.let { calc ->
            val ease = calc(getEasingValue())
            if (!ease.isNaN()) max(0, min(255, (255 * ease).roundToInt())) else 0
        } ?: 255
        bitmapDrawable.alpha = alpha
        bitmapDrawable.draw(canvas)
        canvas.restore()
    }
}