package com.example.omni_ofertas.ui.utils

import android.content.Context
import android.util.AttributeSet
import android.widget.ImageView
import androidx.appcompat.widget.AppCompatImageView
import com.example.omni_ofertas.R



class AspectRatioImage @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0) : AppCompatImageView(context, attrs, defStyleAttr)


{companion object
{
    const val DEFAULT_RATIO = 1F
}
    private var ratio: Float = DEFAULT_RATIO

    init
    {
        attrs.let { val a = context.obtainStyledAttributes(attrs, R.styleable.AspectRatioImage)
            with(a)
            {
                ratio = getFloat(R.styleable.AspectRatioImage_ratio, DEFAULT_RATIO)
                recycle()
            }
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int)
    {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        var width = measuredWidth
        var height = measuredHeight

        if(width == 0 && height == 0)
        return

        if (width > 0)
            height = (width * ratio).toInt()
        else
            width = (height / ratio).toInt()

        setMeasuredDimension(width, height)
    }



}